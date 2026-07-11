import { Component, inject, signal, computed, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { NgIcon } from '@ng-icons/core';
import { PetService } from '../../../services/pet.service';
import { AuthService } from '../../../services/auth.service';
import { PetResponse } from '../../../interfaces/entities/pet.interface';
import { Role } from '../../../../shared/enums/role.enum';

@Component({
  selector: 'app-pet-list',
  standalone: true,
  imports: [NgIcon, RouterLink],
  templateUrl: './pet-list.html',
})
export class PetListComponent implements OnInit {
  private petService = inject(PetService);
  private authService = inject(AuthService);
  private router = inject(Router);

  pets = signal<PetResponse[]>([]);
  loading = signal(false);
  errorMessage = signal<string | null>(null);

  ownerIdQuery = signal<number | null>(null);
  activeOwnerId = signal<number | null>(null);

  isStaff = computed(() => this.authService.hasAnyRole([Role.VETERINARIO, Role.ADMIN]));

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

  onOwnerQueryChange(value: string): void {
    this.ownerIdQuery.set(value ? Number(value) : null);
  }

  searchByOwner(): void {
    const ownerId = this.ownerIdQuery();
    if (!ownerId) {
      this.clearOwnerFilter();
      return;
    }

    this.activeOwnerId.set(ownerId);
    this.loading.set(true);
    this.errorMessage.set(null);

    this.petService.getPetsByOwner(ownerId).subscribe({
      next: (res) => {
        this.pets.set(res.data ?? []);
        this.loading.set(false);
      },
      error: () => {
        this.errorMessage.set('No se pudieron cargar las mascotas de este dueño.');
        this.loading.set(false);
      },
    });
  }

  clearOwnerFilter(): void {
    this.activeOwnerId.set(null);
    this.ownerIdQuery.set(null);
    this.loadDefaultPets();
  }

  goToDetail(pet: PetResponse): void {
    this.router.navigate(['/pets', pet.id], { state: { pet } });
  }
}
