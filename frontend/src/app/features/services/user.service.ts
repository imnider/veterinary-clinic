import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environment/environment';
import { ApiResponse } from '../interfaces/models/api-response.interface';

export interface ClientSearchResult {
  id: number;
  username: string;
  fullName: string;
}

@Injectable({ providedIn: 'root' })
export class ClientSearchService {
  private http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/app-users/search`; //nota

  searchByUsername(username: string): Observable<ApiResponse<ClientSearchResult[]>> {
    return this.http.get<ApiResponse<ClientSearchResult[]>>(this.baseUrl, {
      params: { username },
    });
  }
}
