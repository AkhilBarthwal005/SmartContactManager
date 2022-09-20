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

// delete contact function
function deleteContact(cid) {
    swal({
        title: "Are you sure?",
        text: "Once deleted, you will not be able to recover this contact!",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((willDelete) => {
            if (willDelete) {
                window.location = "/user/delete/"+cid;
            } else {
                swal("Your contact is safe!");
            }
        });
}