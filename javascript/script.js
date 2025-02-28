// JavaScript for OLMS with Live Input Feedback and Dynamic Error Messages

// Helper Function to Show Error Messages
function showError(input, message) {
    const parent = input.parentElement;
    let error = parent.querySelector('.error-message');
    if (!error) {
        error = document.createElement('div');
        error.className = 'error-message text-danger small';
        parent.appendChild(error);
    }
    error.textContent = message;
    input.classList.add('is-invalid');
    input.classList.remove('is-valid');
}

// Helper Function to Show Success
function showSuccess(input) {
    const parent = input.parentElement;
    const error = parent.querySelector('.error-message');
    if (error) error.remove();
    input.classList.remove('is-invalid');
    input.classList.add('is-valid');
}

// Email Validation
function validateEmail(input) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (emailRegex.test(input.value.trim())) {
        showSuccess(input);
        return true;
    } else {
        showError(input, 'Please enter a valid email address.');
        return false;
    }
}

// Password Strength Validation
function validatePassword(input) {
    const minLength = 8;
    const password = input.value.trim();

    if (password.length < minLength) {
        showError(input, `Password must be at least ${minLength} characters.`);
        return false;
    }
    if (!/[A-Z]/.test(password)) {
        showError(input, 'Password must include at least one uppercase letter.');
        return false;
    }
    if (!/[a-z]/.test(password)) {
        showError(input, 'Password must include at least one lowercase letter.');
        return false;
    }
    if (!/[0-9]/.test(password)) {
        showError(input, 'Password must include at least one digit.');
        return false;
    }
    showSuccess(input);
    return true;
}

// Live Validation
function addLiveValidation() {
    const emailInputs = document.querySelectorAll('input[type="email"]');
    const passwordInputs = document.querySelectorAll('input[type="password"]');

    emailInputs.forEach((input) => {
        input.addEventListener('input', () => validateEmail(input));
    });

    passwordInputs.forEach((input) => {
        input.addEventListener('input', () => validatePassword(input));
    });
}

// Form Validation on Submission
function validateForm(event) {
    const form = event.target;
    const email = form.querySelector('input[type="email"]');
    const password = form.querySelector('input[type="password"]');
    let isValid = true;

    if (!validateEmail(email)) isValid = false;
    if (!validatePassword(password)) isValid = false;

    if (!isValid) {
        event.preventDefault(); // Prevent form submission if validation fails
    }
}

// Attach Event Listeners on DOMContentLoaded
document.addEventListener('DOMContentLoaded', () => {
    addLiveValidation();

    const forms = document.querySelectorAll('form');
    forms.forEach((form) => form.addEventListener('submit', validateForm));
});
