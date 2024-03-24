const submit=document.querySelector(".submit");




submit.addEventListener("click", function(event) {
    event.preventDefault(); // Prevent default form submission
    
        window.location.href = "Login.html"; // Redirect to login.html
    
});
