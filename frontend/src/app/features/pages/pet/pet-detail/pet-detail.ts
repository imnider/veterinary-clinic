import { Component, inject, signal, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { NgIcon } from '@ng-icons/core';
import { PetService } from '../../../services/pet.service';
import { PetResponse } from '../../../interfaces/entities/pet.interface';

@Component({
  selector: 'app-pet-detail',
  standalone: true,
  imports: [NgIcon, RouterLink],
  templateUrl: './pet-detail.html',
})
export class PetDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private petService = inject(PetService);

  pet = signal<PetResponse | null>(null);
  isLoading = signal(true);
  errorMessage = signal<string | null>(null);

  ngOnInit(): void {
    const petId = Number(this.route.snapshot.paramMap.get('id'));
    const state = this.router.getCurrentNavigation()?.extras.state ?? history.state;
    if (state?.['pet']) {
      this.pet.set(state['pet']);
      this.isLoading.set(false);
    }

    if (!petId) {
      this.isLoading.set(false);
      this.errorMessage.set('Identificador de mascota inválido.');
      return;
    }

    this.petService.getPetById(petId).subscribe({
      next: (res) => {
        this.pet.set(res.data);
        this.isLoading.set(false);
      },
      error: () => {
        this.isLoading.set(false);
        this.errorMessage.set('No se pudo cargar la información de la mascota.');
      },
    });
  }

  goToMedicalHistory(): void {
    const pet = this.pet();
    if (!pet) return;
    this.router.navigate(['/pets', pet.id, 'history']);
  }
}
