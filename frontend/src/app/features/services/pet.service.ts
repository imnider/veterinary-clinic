import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PetRequest, PetResponse } from '../interfaces/entities/pet.interface';
import { environment } from '../../../environment/environment';
import { ApiResponse } from '../interfaces/models/api-response.interface';

@Injectable({ providedIn: 'root' })
export class PetService {
  private http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/pets`;

  registerPet(request: PetRequest, photo?: File | null): Observable<ApiResponse<PetResponse>> {
    const formData = new FormData();
    formData.append('data', new Blob([JSON.stringify(request)], { type: 'application/json' }));
    if (photo) {
      formData.append('photo', photo);
    }
    return this.http.post<ApiResponse<PetResponse>>(this.baseUrl, formData);
  }

  getMyPets(): Observable<ApiResponse<PetResponse[]>> {
    return this.http.get<ApiResponse<PetResponse[]>>(`${this.baseUrl}/me`);
  }

  getPetsByOwner(ownerId: number): Observable<ApiResponse<PetResponse[]>> {
    return this.http.get<ApiResponse<PetResponse[]>>(`${this.baseUrl}/${ownerId}`);
  }

  getAllPets(): Observable<ApiResponse<PetResponse[]>> {
    return this.http.get<ApiResponse<PetResponse[]>>(this.baseUrl);
  }

  getPetById(id: number): Observable<ApiResponse<PetResponse>> {
    return this.http.get<ApiResponse<PetResponse>>(`${this.baseUrl}/detail/${id}`);
  }
}
