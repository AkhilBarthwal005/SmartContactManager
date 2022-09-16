console.log("This is text come from script file");

let backdrop = document.querySelector(".backdrop");
let side_bar = document.querySelector(".side-bar");
let main_content = document.querySelector(".main-content");
let menu_bar = document.querySelector(".menu-bar");


menu_bar.addEventListener("click",()=>{
    console.log("menu-bar");
    backdrop.style.display="block";
    side_bar.style.display="block";
    main_content.style.display="none";
});

backdrop.addEventListener("click",()=>{
    console.log("backdrop");
    backdrop.style.display="none";
    side_bar.style.display="none";
    main_content.style.display="block";
})