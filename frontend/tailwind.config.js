/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,ts}"],
  theme: {
    extend: {
      screens: {
        navBar: "1100px"
      },
      fontFamily: {
        sans: ['Inter', 'sans-serif']
      },
      colors: {
        brand1: "#F7B267",
        brand2: "#F79D65",
        brand3: "#F4845F",
        brand4: "#F27059",
        brand5: "#F25C54",
      }
    },
  },
  plugins: [],
}
