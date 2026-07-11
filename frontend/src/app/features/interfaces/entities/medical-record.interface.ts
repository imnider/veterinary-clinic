export interface MedicalRecordResponse {
  id: number;
  petId: number;
  appointmentId: number;
  veterinarianName: string;
  recordDate: string;
  diagnosis: string;
  notes: string;
  recordedWeight: number;
}

export interface CreateMedicalRecordRequest {
  appointmentId: number;
  diagnosis: string;
  notes: string;
  recordedWeight: number;
}
