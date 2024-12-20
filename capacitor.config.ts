import type { CapacitorConfig } from '@capacitor/cli';

const { APP_ENV = 'production' } = process.env;

const config: CapacitorConfig = {
  appId: 'org.DPS Wallet.app',
  appName: 'DPS Wallet',
  webDir: 'dist',
  server: {
    androidScheme: 'https',
    hostname: 'DPS Wallet.local',
  },
  android: {
    path: 'mobile/android',
    includePlugins: [
      '@capacitor-mlkit/barcode-scanning',
      '@capacitor/app',
      '@capacitor/clipboard',
      '@capacitor/haptics',
      '@capacitor/status-bar',
      '@capgo/capacitor-native-biometric',
      '@capgo/native-audio',
      '@mauricewegner/capacitor-navigation-bar',
      '@sina_kh/mtw-capacitor-status-bar',
      'capacitor-native-settings',
      'capacitor-plugin-safe-area',
      'cordova-plugin-inappbrowser',
      'native-dialog',
      'native-bottom-sheet',
      'capacitor-secure-storage-plugin',
      '@capacitor/app-launcher',
    ],
    webContentsDebuggingEnabled: APP_ENV !== 'production',
  },
  ios: {
    path: 'mobile/ios',
    scheme: 'DPS Wallet',
    webContentsDebuggingEnabled: APP_ENV !== 'production',
  },
  plugins: {
    SplashScreen: {
      launchAutoHide: false,
    },
    CapacitorHttp: {
      enabled: true,
    },
  },
};

export default config;
