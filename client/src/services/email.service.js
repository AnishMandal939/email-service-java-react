import {customAxios} from './helper'
export async function sendEmail(emailData) {
   const result = (await  customAxios.post('/email/send', emailData)).data;
   return result;
}
