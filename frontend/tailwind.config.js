const { addDynamicIconSelectors } = require('@iconify/tailwind'); // opcional, ignora si no usas iconify

module.exports = {
  content: ['./src/**/*.{html,ts}'],
  theme: {
    extend: {
      fontFamily: {
        sans: ['"Poppins"', 'system-ui', 'sans-serif'],
      },
    },
  },
  daisyui: {
    themes: [
      {
        ghibli: {
          primary: '#5b8c5a',
          secondary: '#7fa9c9',
          accent: '#e0b384',
          neutral: '#4a4238',
          'base-100': '#fdfbf5',
          'base-200': '#f3ecd9',
          'base-300': '#e6dcc3',
          info: '#7fa9c9',
          success: '#5b8c5a',
          warning: '#e0b384',
          error: '#c96a5a',
        },
      },
    ],
  },
  plugins: [require('daisyui')],
};
