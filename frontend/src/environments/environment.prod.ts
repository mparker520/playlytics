export const environment = {
  production: true,
  apiUrl: (window as any).__env?.apiUrl || 'https://playlytics.onrender.com'
};
