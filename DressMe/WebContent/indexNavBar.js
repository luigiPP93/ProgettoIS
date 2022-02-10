const menu = document.querySelector(".menu");
const navOpen = document.querySelector(".hamburger");//restituisce solo il primo elemento che corrisponde ai selettori specificati.
const navClose = document.querySelector(".close");

const navLeft = menu.getBoundingClientRect().left;
navOpen.addEventListener("click", () => {//aprire la schermata a sinistra
  if (navLeft < 0) {
    menu.classList.add("show");
    document.body.classList.add("show");
    navBar.classList.add("show");
  }
});

navClose.addEventListener("click", () => {//quando clicco la x la schermata la chiude
  if (navLeft < 0) {
    menu.classList.remove("show");
    document.body.classList.remove("show");
    navBar.classList.remove("show");
  }
});
// Fixed Nav
const navBar = document.querySelector(".nav");
const navHeight = navBar.getBoundingClientRect().height;
window.addEventListener("scroll", () => { //quando scrollo verso il basso conpare la nav bar
  const scrollHeight = window.pageYOffset;//verticale
  if (scrollHeight > navHeight) { //se l'altezz della paginaYoffset è > di quella della navBar
    navBar.classList.add("fix-nav");
  } else {
    navBar.classList.remove("fix-nav");
  }
});


gsap.from(".logo", { opacity: 0, duration: 1, delay: 0.5, y: -10 });//animare gli oggetti
gsap.from(".hamburger", { opacity: 0, duration: 1, delay: 1, x: 20 });
gsap.from(".hero-img", { opacity: 0, duration: 1, delay: 1.5, x: -200 });
gsap.from(".hero-content h2", { opacity: 0, duration: 1, delay: 2, y: -50 });
gsap.from(".hero-content h1", { opacity: 0, duration: 1, delay: 2.5, y: -45 });
gsap.from(".hero-content a", { opacity: 0, duration: 1, delay: 3.5, y: 50 });