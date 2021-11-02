import { createAction, props } from '@ngrx/store';
import { IUser } from '../../interfaces/user';

export const setUser = createAction(
    '[User] Set User',
    props<{ user: IUser }>()
);