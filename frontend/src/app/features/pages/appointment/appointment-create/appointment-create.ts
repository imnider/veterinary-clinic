import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { NgIcon } from '@ng-icons/core';
import { PetService } from '../../../services/pet.service';
import { AppointmentService } from '../../../services/appointment.service';
import { PetResponse } from '../../../interfaces/entities/pet.interface';
import { CreateAppointmentRequest } from '../../../interfaces/entities/appointment.interface';
import { APPOINTMENT_TYPES } from '../../../../shared/constants/appointment.constants';
import { UserResponse } from '../../../interfaces/entities/user.interface';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-appointment-create',
  standalone: true,
  imports: [ReactiveFormsModule, NgIcon, RouterLink],
  templateUrl: './appointment-create.html',
})
export class AppointmentCreateComponent implements OnInit {
  private fb = inject(FormBuilder);
  private petService = inject(PetService);
  private appointmentService = inject(AppointmentService);
  private userService = inject(UserService);
  private router = inject(Router);

  readonly appointmentTypes = APPOINTMENT_TYPES;
  readonly veterinarians = signal<UserResponse[]>([]);

  myPets = signal<PetResponse[]>([]);
  isLoadingPets = signal(true);
  isLoadingVeterinarians = signal(true);
  isSubmitting = signal(false);
  errorMessage = signal<string | null>(null);

  form = this.fb.nonNullable.group({
    petId: [null as number | null, Validators.required],
    veterinarianId: [null as number | null, Validators.required],
    appointmentDate: ['', Validators.required, this.futureDateValidator],
    appointmentType: ['', Validators.required],
    reason: ['', [Validators.required, Validators.maxLength(255)]],
  });

  ngOnInit(): void {
    this.petService.getMyPets().subscribe({
      next: (res) => {
        this.myPets.set(res.data ?? []);
        this.isLoadingPets.set(false);
      },
      error: () => {
        this.isLoadingPets.set(false);
        this.errorMessage.set('No se pudieron cargar tus mascotas.');
      },
    });

    this.userService.getUsers().subscribe({
      next: (res) => {
        this.veterinarians.set(res.data ?? []);
        this.isLoadingVeterinarians.set(false);
      },
      error: () => {
        this.isLoadingVeterinarians.set(false);
        this.errorMessage.set('No se pudieron cargar los veterinarios.');
      },
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.isSubmitting.set(true);
    this.errorMessage.set(null);

    const raw = this.form.getRawValue();
    const request: CreateAppointmentRequest = {
      petId: raw.petId!,
      veterinarianId: raw.veterinarianId!,
      appointmentDate: raw.appointmentDate,
      appointmentType: raw.appointmentType,
      reason: raw.reason,
    };

    this.appointmentService.createAppointment(request).subscribe({
      next: () => {
        this.isSubmitting.set(false);
        this.router.navigate(['/appointments']);
      },
      error: (err) => {
        this.isSubmitting.set(false);
        this.errorMessage.set(err?.error?.message ?? 'No se pudo agendar la cita.');
      },
    });
  }

  minDate = new Date().toISOString().slice(0, 16);
  private futureDateValidator(control: AbstractControl) {
    if (!control.value) return null;
    return new Date(control.value) > new Date() ? null : { pastDate: true };
  }
}
