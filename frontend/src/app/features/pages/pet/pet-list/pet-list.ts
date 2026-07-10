import { Component, inject, signal, computed, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgIcon } from '@ng-icons/core';
import { PetService } from '../../../services/pet.service';
import { AuthService } from '../../../services/auth.service';
import { PetResponse } from '../../../interfaces/entities/pet.interface';
import { Role } from '../../../../shared/enums/role.enum';
import { ClientSearchResult, ClientSearchService } from '../../../services/user.service';

@Component({
  selector: 'app-pet-list',
  standalone: true,
  imports: [NgIcon],
  templateUrl: './pet-list.html',
})
export class PetListComponent implements OnInit {
  private petService = inject(PetService);
  private clientSearchService = inject(ClientSearchService);
  private authService = inject(AuthService);
  private router = inject(Router);

  pets = signal<PetResponse[]>([]);
  loading = signal(false);
  errorMessage = signal<string | null>(null);

  usernameQuery = signal('');
  matchedClients = signal<ClientSearchResult[]>([]);
  activeClient = signal<ClientSearchResult | null>(null);

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

  //   searchClients(): void {
  //     const username = this.usernameQuery().trim();
  //     if (!username) {
  //       this.matchedClients.set([]);
  //       this.activeClient.set(null);
  //       this.loadDefaultPets();
  //       return;
  //     }

  //     this.loading.set(true);
  //     this.clientSearchService.searchByUsername(username).subscribe({
  //       next: (res) => {
  //         this.matchedClients.set(res.data ?? []);
  //         this.loading.set(false);
  //       },
  //       error: () => {
  //         this.errorMessage.set('No se pudo buscar el cliente.');
  //         this.loading.set(false);
  //       },
  //     });
  //   }

  selectClient(client: ClientSearchResult): void {
    this.activeClient.set(client);
    this.matchedClients.set([]);
    this.loading.set(true);

    this.petService.getPetsByOwner(client.id).subscribe({
      next: (res) => {
        this.pets.set(res.data ?? []);
        this.loading.set(false);
      },
      error: () => {
        this.errorMessage.set('No se pudieron cargar las mascotas de este cliente.');
        this.loading.set(false);
      },
    });
  }

  clearClientFilter(): void {
    this.activeClient.set(null);
    this.usernameQuery.set('');
    this.loadDefaultPets();
  }

  goToDetail(pet: PetResponse): void {
    // nota: cuando exista, routerLink="/pets/{{pet.id}}"
    this.router.navigate(['/pets', pet.id], { state: { pet } });
  }
}
