export interface IUser {
  id: number;
  nombre: string;
  apellido: string;
  username: string;
  accessToken: string | null;
  terceroId: number;
  role: string;
}