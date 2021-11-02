import { createReducer, on, Action } from '@ngrx/store';
import { IUser } from '../../interfaces/user';
import { setUser } from '../actions/user.actions';

export const userInitialState: IUser = {
  id: 0,
  nombre: '',
  apellido: '',
  username: '',
  role: '',
  terceroId: 0,
  accessToken: ''
}

const _userReducer = createReducer(userInitialState,

  on(setUser, (state, { user }) => ({
    ...user,
    accessToken: user.accessToken ? user.accessToken : localStorage.getItem('token')
  })),

);

export function userReducer(state: IUser, action: Action) {
  return _userReducer(state, action);
}
