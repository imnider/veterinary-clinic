import { Component, inject, signal, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgIcon } from '@ng-icons/core';
import { PetResponse } from '../../../interfaces/entities/pet.interface';

@Component({
  selector: 'app-pet-detail',
  standalone: true,
  imports: [NgIcon],
  templateUrl: './pet-detail.html',
})
export class PetDetailComponent implements OnInit {
  private router = inject(Router);

  pet = signal<PetResponse | null>(null);

  ngOnInit(): void {
    // Como aún no existe GET /api/pets/{id}, dependemos de que la mascota
    // venga por router state desde pet-list. Si el usuario entra directo
    // por URL o recarga, no tenemos cómo recuperarla todavía.
    const state = this.router.getCurrentNavigation()?.extras.state ?? history.state;

    if (state?.['pet']) {
      this.pet.set(state['pet']);
    }

    // TODO backend: cuando exista petService.getPetById(id), reemplazar
    // lo de arriba por una carga real usando el :id de la ruta, con
    // fallback a router state para evitar el request si ya lo tenemos.
  }

  goToMedicalHistory(): void {
    const pet = this.pet();
    if (!pet) return;
    // TODO: ruta de historial médico aún no implementada.
    this.router.navigate(['/pets', pet.id, 'medical-history']);
  }
}
