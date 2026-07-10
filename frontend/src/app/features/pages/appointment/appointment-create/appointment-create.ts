// import { Component, OnInit, inject, signal } from '@angular/core';
// import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
// import { Router } from '@angular/router';
// import { PetService } from '../../../services/pet.service';
// import { PetResponse } from '../../../interfaces/entities/pet.interface';

// @Component({
//   selector: 'app-appointment-create',
//   standalone: true,
//   imports: [ReactiveFormsModule],
//   templateUrl: './appointment-create.html',
// })
// export class AppointmentCreateComponent implements OnInit {
//   private fb = inject(FormBuilder);
//   private petService = inject(PetService);
//   private appUserService = inject(UserService);
//   private appointmentService = inject(AppointmentService);
//   private router = inject(Router);

//   myPets = signal<PetResponse[]>([]);
//   veterinarians = signal<AppUserResponse[]>([]);
//   isSubmitting = signal(false);
//   errorMessage = signal<string | null>(null);

//   form = this.fb.nonNullable.group({
//     petId: [null as number | null, Validators.required],
//     veterinarianId: [null as number | null, Validators.required],
//     appointmentDate: ['', Validators.required],
//     appointmentType: ['', Validators.required],
//     reason: ['', [Validators.required, Validators.maxLength(255)]],
//   });

//   ngOnInit(): void {
//     this.petService.getMyPets().subscribe({
//       next: (res) => this.myPets.set(res.data),
//     });
//     this.appUserService.getVeterinarians().subscribe({
//       next: (res) => this.veterinarians.set(res.data),
//     });
//   }

//   onSubmit(): void {
//     if (this.form.invalid) {
//       this.form.markAllAsTouched();
//       return;
//     }

//     this.isSubmitting.set(true);
//     this.errorMessage.set(null);

//     const request = this.form.getRawValue() as CreateAppointmentRequest;

//     this.appointmentService.createAppointment(request).subscribe({
//       next: (response) => {
//         this.isSubmitting.set(false);
//         this.router.navigate(['/pets', request.petId, 'history']);
//       },
//       error: (err) => {
//         this.isSubmitting.set(false);
//         this.errorMessage.set(err?.error?.message ?? 'No se pudo agendar la cita.');
//       },
//     });
//   }
// }
