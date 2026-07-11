import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { NgIcon } from '@ng-icons/core';
import { AppointmentService } from '../../../services/appointment.service';
import { AuthService } from '../../../services/auth.service';
import { AppointmentResponse } from '../../../interfaces/entities/appointment.interface';
import { Role } from '../../../../shared/enums/role.enum';
import {
  APPOINTMENT_STATUSES,
  getAppointmentTypeLabel,
  getStatusOption,
} from '../../../../shared/constants/appointment.constants';

type FilterMode = 'all' | 'pending';

@Component({
  selector: 'app-appointment-list',
  standalone: true,
  imports: [DatePipe, RouterLink, NgIcon],
  templateUrl: './appointment-list.html',
})
export class AppointmentListComponent implements OnInit {
  private appointmentService = inject(AppointmentService);
  private authService = inject(AuthService);
  private router = inject(Router);

  readonly getAppointmentTypeLabel = getAppointmentTypeLabel;
  readonly getStatusOption = getStatusOption;
  readonly statuses = APPOINTMENT_STATUSES;

  appointments = signal<AppointmentResponse[]>([]);
  loading = signal(false);
  errorMessage = signal<string | null>(null);
  filter = signal<FilterMode>('pending');
  updatingId = signal<number | null>(null);

  isStaff = computed(() => this.authService.hasAnyRole([Role.VETERINARIO, Role.ADMIN]));

  sortedAppointments = computed(() =>
    [...this.appointments()].sort(
      (a, b) => new Date(a.appointmentDate).getTime() - new Date(b.appointmentDate).getTime(),
    ),
  );

  ngOnInit(): void {
    this.load();
  }

  setFilter(mode: FilterMode): void {
    if (this.filter() === mode) return;
    this.filter.set(mode);
    this.load();
  }

  load(): void {
    this.loading.set(true);
    this.errorMessage.set(null);

    this.appointmentService.getAppointments(this.filter() === 'pending').subscribe({
      next: (res) => {
        this.appointments.set(res.data ?? []);
        this.loading.set(false);
      },
      error: () => {
        this.errorMessage.set('No se pudieron cargar las citas.');
        this.loading.set(false);
      },
    });
  }

  changeStatus(appointment: AppointmentResponse, status: string): void {
    this.updatingId.set(appointment.id);
    this.appointmentService.updateAppointmentStatus(appointment.id, { status }).subscribe({
      next: () => {
        this.updatingId.set(null);
        this.load();
      },
      error: () => {
        this.updatingId.set(null);
        this.errorMessage.set('No se pudo actualizar el estado de la cita.');
      },
    });
  }

  goToRecord(appointment: AppointmentResponse): void {
    this.router.navigate(['/appointments', appointment.id, 'record']);
  }
}
