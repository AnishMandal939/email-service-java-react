import axios from 'axios';
import { BASE_URL } from '../constants/Endpoints';

const baseURL = BASE_URL;
export const customAxios  = axios.create({
    baseURL: baseURL
})