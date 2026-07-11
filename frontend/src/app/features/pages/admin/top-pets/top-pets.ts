import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NgIcon } from '@ng-icons/core';
import { AppointmentService } from '../../../services/appointment.service';
import { AuthService } from '../../../services/auth.service';
import { Role } from '../../../../shared/enums/role.enum';

interface PetAppointmentCount {
  petId: number;
  petName: string;
  totalAppointments: number;
}

@Component({
  selector: 'app-top-pets',
  standalone: true,
  imports: [RouterLink, NgIcon],
  templateUrl: './top-pets.html',
})
export class TopPetsComponent implements OnInit {
  private appointmentService = inject(AppointmentService);
  private authService = inject(AuthService);

  isAdmin = computed(() => this.authService.hasAnyRole([Role.ADMIN]));

  loading = signal(false);
  errorMessage = signal<string | null>(null);
  topPets = signal<PetAppointmentCount[]>([]);

  ngOnInit(): void {
    if (!this.isAdmin()) return;
    this.load();
  }

  load(): void {
    this.loading.set(true);
    this.errorMessage.set(null);

    // No existe un endpoint en el backend para esta consulta, así que
    // se calcula en el frontend a partir de todas las citas.
    this.appointmentService.getAppointments().subscribe({
      next: (res) => {
        this.topPets.set(this.computeTopPets(res.data ?? []));
        this.loading.set(false);
      },
      error: () => {
        this.errorMessage.set('No se pudieron cargar las citas para calcular el reporte.');
        this.loading.set(false);
      },
    });
  }

  private computeTopPets(
    appointments: { petId: number; petName: string }[],
  ): PetAppointmentCount[] {
    const countsByPet = new Map<number, PetAppointmentCount>();

    for (const appt of appointments) {
      const existing = countsByPet.get(appt.petId);
      if (existing) {
        existing.totalAppointments++;
      } else {
        countsByPet.set(appt.petId, {
          petId: appt.petId,
          petName: appt.petName,
          totalAppointments: 1,
        });
      }
    }

    const counts = [...countsByPet.values()];
    if (counts.length === 0) return [];

    const maxCount = Math.max(...counts.map((c) => c.totalAppointments));

    return counts
      .filter((c) => c.totalAppointments === maxCount)
      .sort((a, b) => b.totalAppointments - a.totalAppointments);
  }
}
