// pages/pet-history/pet-history.component.ts
import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppointmentResponse } from '../../interfaces/entities/appointment.interface';
import { MedicalRecordResponse } from '../../interfaces/entities/medical-record.interface';
import { PetService } from '../../services/pet.service';
import { AppointmentService } from '../../services/appointment.service';
import { MedicalRecordService } from '../../services/medical-record.service';
import { PetResponse } from '../../interfaces/entities/pet.interface';

type TimelineEntry =
  | { type: 'appointment'; date: string; data: AppointmentResponse }
  | { type: 'record'; date: string; data: MedicalRecordResponse };

@Component({
  selector: 'app-medical-record',
  standalone: true,
  templateUrl: './medical-record.html',
})
export class PetHistoryComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private petService = inject(PetService);
  private appointmentService = inject(AppointmentService);
  private medicalRecordService = inject(MedicalRecordService);

  pet = signal<PetResponse | null>(null);
  appointments = signal<AppointmentResponse[]>([]);
  medicalRecords = signal<MedicalRecordResponse[]>([]);
  isLoading = signal(true);
  errorMessage = signal<string | null>(null);

  timeline = computed<TimelineEntry[]>(() => {
    const entries: TimelineEntry[] = [
      ...this.appointments().map((a) => ({
        type: 'appointment' as const,
        date: a.appointmentDate,
        data: a,
      })),
      ...this.medicalRecords().map((r) => ({
        type: 'record' as const,
        date: r.recordDate,
        data: r,
      })),
    ];
    return entries.sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime());
  });

  ngOnInit(): void {
    const petId = Number(this.route.snapshot.paramMap.get('id'));

    this.petService.getPetById(petId).subscribe({
      next: (res) => this.pet.set(res.data),
      error: () => this.errorMessage.set('No se pudo cargar la mascota.'),
    });

    this.appointmentService.getAppointmentsByPet(petId).subscribe({
      next: (res) => this.appointments.set(res.data),
    });

    this.medicalRecordService.getMedicalRecordsByPet(petId).subscribe({
      next: (res) => {
        this.medicalRecords.set(res.data);
        this.isLoading.set(false);
      },
      error: () => {
        this.isLoading.set(false);
        this.errorMessage.set('No se pudo cargar el historial.');
      },
    });
  }
}
