import { Component, inject, signal } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgIcon } from '@ng-icons/core';
import { PetService } from '../../../services/pet.service';
import { PetRequest } from '../../../interfaces/entities/pet.interface';

@Component({
  selector: 'app-pet-register',
  standalone: true,
  imports: [ReactiveFormsModule, NgIcon],
  templateUrl: './pet-register.html',
})
export class PetRegisterComponent {
  private fb = inject(FormBuilder);
  private petService = inject(PetService);
  private router = inject(Router);

  isSubmitting = signal(false);
  errorMessage = signal<string | null>(null);
  photoPreview = signal<string | null>(null);
  selectedPhoto = signal<File | null>(null);

  form = this.fb.nonNullable.group({
    name: ['', [Validators.required, Validators.maxLength(50)]],
    species: ['', Validators.required],
    breed: [''],
    birthDate: ['', Validators.required],
    weight: [null as number | null, [Validators.min(0)]],
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
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.isSubmitting.set(true);
    this.errorMessage.set(null);

    const request = this.form.getRawValue() as PetRequest;

    this.petService.registerPet(request, this.selectedPhoto()).subscribe({
      next: (response) => {
        this.isSubmitting.set(false);
        if (!response.data) {
          this.errorMessage.set('La respuesta del servidor no contiene los datos de la mascota.');
          return;
        }
        this.router.navigate(['/pets', response.data.id, 'history']);
      },
      error: (err) => {
        this.isSubmitting.set(false);
        this.errorMessage.set(err?.error?.message ?? 'No se pudo registrar la mascota.');
      },
    });
  }
}
