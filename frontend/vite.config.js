import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    host: '0.0.0.0', // allows access via server IP
    port: 5173       // default Vite dev port
  },
  build: {
    outDir: 'dist',  // output folder for `npm run build`
    emptyOutDir: true
  }
});