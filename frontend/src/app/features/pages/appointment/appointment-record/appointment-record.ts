import { Component, OnInit, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { NgIcon } from '@ng-icons/core';
import { MedicalRecordService } from '../../../services/medical-record.service';
import { AppointmentService } from '../../../services/appointment.service';
import { CreateMedicalRecordRequest } from '../../../interfaces/entities/medical-record.interface';

@Component({
  selector: 'app-appointment-record',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, NgIcon],
  templateUrl: './appointment-record.html',
})
export class AppointmentRecordComponent implements OnInit {
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private medicalRecordService = inject(MedicalRecordService);
  private appointmentService = inject(AppointmentService);

  appointmentId!: number;
  isSubmitting = signal(false);
  errorMessage = signal<string | null>(null);

  form = this.fb.nonNullable.group({
    diagnosis: ['', [Validators.required, Validators.maxLength(500)]],
    notes: ['', Validators.maxLength(1000)],
    recordedWeight: [null as number | null, [Validators.required, Validators.min(0)]],
  });

  ngOnInit(): void {
    this.appointmentId = Number(this.route.snapshot.paramMap.get('id'));
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.isSubmitting.set(true);
    this.errorMessage.set(null);

    const raw = this.form.getRawValue();
    const request: CreateMedicalRecordRequest = {
      appointmentId: this.appointmentId,
      diagnosis: raw.diagnosis,
      notes: raw.notes,
      recordedWeight: raw.recordedWeight!,
    };

    this.medicalRecordService.createMedicalRecord(request).subscribe({
      next: () => {
        this.appointmentService
          .updateAppointmentStatus(this.appointmentId, { status: 'ATENDIDA' })
          .subscribe({
            next: () => {
              this.isSubmitting.set(false);
              this.router.navigate(['/appointments']);
            },
            error: () => {
              this.isSubmitting.set(false);
              this.router.navigate(['/appointments']);
            },
          });
      },
      error: (err) => {
        this.isSubmitting.set(false);
        this.errorMessage.set(err?.error?.message ?? 'No se pudo registrar la atención.');
      },
    });
  }
}
