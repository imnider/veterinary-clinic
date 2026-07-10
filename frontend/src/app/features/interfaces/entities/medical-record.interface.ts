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
