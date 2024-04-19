import dotenv from 'dotenv'
import { defineConfig } from 'vite'
// import react from '@vitejs/plugin-react'
import react from '@vitejs/plugin-react-swc';

dotenv.config();

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  // define process env
  define: {
    // eslint-disable-next-line no-undef
    'process.env': process.env
  }
})
