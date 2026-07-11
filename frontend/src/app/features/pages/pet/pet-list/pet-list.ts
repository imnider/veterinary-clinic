import { Component, inject, signal, computed, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { NgIcon } from '@ng-icons/core';
import { PetService } from '../../../services/pet.service';
import { AuthService } from '../../../services/auth.service';
import { PetResponse } from '../../../interfaces/entities/pet.interface';
import { Role } from '../../../../shared/enums/role.enum';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-pet-list',
  standalone: true,
  imports: [NgIcon, RouterLink, FormsModule],
  templateUrl: './pet-list.html',
})
export class PetListComponent implements OnInit {
  private petService = inject(PetService);
  private authService = inject(AuthService);
  private router = inject(Router);

  pets = signal<PetResponse[]>([]);
  loading = signal(false);
  errorMessage = signal<string | null>(null);

  ownerNameQuery = signal<string>('');

  isStaff = computed(() => this.authService.hasAnyRole([Role.VETERINARIO, Role.ADMIN]));

  filteredPets = computed(() => {
    const query = this.ownerNameQuery().trim().toLowerCase();
    if (!query) return this.pets();
    return this.pets().filter((pet) => pet.ownerName.toLowerCase().includes(query));
  });

  ngOnInit(): void {
    this.loadDefaultPets();
  }

  private loadDefaultPets(): void {
    this.loading.set(true);
    this.errorMessage.set(null);

    const request$ = this.isStaff() ? this.petService.getAllPets() : this.petService.getMyPets();

    request$.subscribe({
      next: (res) => {
        this.pets.set(res.data ?? []);
        this.loading.set(false);
      },
      error: () => {
        this.errorMessage.set('No se pudieron cargar las mascotas.');
        this.loading.set(false);
      },
    });
  }

  goToDetail(pet: PetResponse): void {
    this.router.navigate(['/pets', pet.id], { state: { pet } });
  }
}
