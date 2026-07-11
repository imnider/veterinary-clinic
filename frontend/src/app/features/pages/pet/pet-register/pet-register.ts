import { Component, computed, inject, signal } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgIcon } from '@ng-icons/core';
import { PetService } from '../../../services/pet.service';
import { AuthService } from '../../../services/auth.service';
import { PetRequest } from '../../../interfaces/entities/pet.interface';
import { Role } from '../../../../shared/enums/role.enum';
import { PET_SEX, PET_SPECIES } from '../../../../shared/constants/appointment.constants';

@Component({
  selector: 'app-pet-register',
  standalone: true,
  imports: [ReactiveFormsModule, NgIcon],
  templateUrl: './pet-register.html',
})
export class PetRegisterComponent {
  private fb = inject(FormBuilder);
  private petService = inject(PetService);
  private authService = inject(AuthService);
  private router = inject(Router);

  readonly species = PET_SPECIES;
  readonly sexes = PET_SEX;

  isSubmitting = signal(false);
  errorMessage = signal<string | null>(null);
  photoPreview = signal<string | null>(null);
  selectedPhoto = signal<File | null>(null);

  isStaff = computed(() => this.authService.hasAnyRole([Role.VETERINARIO, Role.ADMIN]));

  form = this.fb.nonNullable.group({
    ownerId: [null as number | null],
    name: ['', [Validators.required, Validators.maxLength(50)]],
    species: ['', Validators.required],
    breed: [''],
    birthDate: ['', Validators.required],
    sex: ['', Validators.required],
    weight: [null as number | null, [Validators.required, Validators.min(0)]],
    neutered: [false],
  });

  onPhotoSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0] ?? null;
    this.selectedPhoto.set(file);

    if (file) {
      const reader = new FileReader();
      reader.onload = () => this.photoPreview.set(reader.result as string);
      reader.readAsDataURL(file);
    } else {
      this.photoPreview.set(null);
    }
  }

  onSubmit(): void {
    if (this.isStaff()) {
      this.form.controls.ownerId.addValidators(Validators.required);
      this.form.controls.ownerId.updateValueAndValidity();
    }

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.isSubmitting.set(true);
    this.errorMessage.set(null);

    const raw = this.form.getRawValue();
    const ownerId = this.isStaff() ? raw.ownerId! : (this.authService.currentUser()?.id ?? null);

    if (!ownerId) {
      this.isSubmitting.set(false);
      this.errorMessage.set('No se pudo determinar el dueño de la mascota.');
      return;
    }

    const request: PetRequest = {
      ownerId,
      name: raw.name,
      species: raw.species,
      breed: raw.breed,
      birthDate: raw.birthDate,
      sex: raw.sex,
      weight: raw.weight!,
      neutered: raw.neutered,
    };

    this.petService.registerPet(request, this.selectedPhoto()).subscribe({
      next: (response) => {
        this.isSubmitting.set(false);
        if (!response.data) {
          this.errorMessage.set('La respuesta del servidor no contiene los datos de la mascota.');
          return;
        }
        this.router.navigate(['/pets', response.data.id]);
      },
      error: (err) => {
        this.isSubmitting.set(false);
        this.errorMessage.set(err?.error?.message ?? 'No se pudo registrar la mascota.');
      },
    });
  }
}
