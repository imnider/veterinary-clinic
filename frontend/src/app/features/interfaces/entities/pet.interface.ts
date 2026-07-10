export interface PetRequest {
  ownerId: number;
  name: string;
  species: string;
  breed: string;
  birthDate: string;
  sex: string;
  weight: number;
  neutered: boolean; //si/no
  photoUrl?: string | null;
}

export interface PetResponse {
  id: number;
  name: string;
  species: string;
  breed: string;
  birthDate: string;
  sex: string;
  weight: number;
  neutered: boolean;
  photoUrl?: string | null;
  ownerName: string;
}
