// --- Password Visibility Toggle Logic ---
        const togglePassword = document.querySelector('#togglePassword');
        const password = document.querySelector('#passwordInput');
        const eyeIcon = document.querySelector('#eyeIcon');

        togglePassword.addEventListener('click', function() {
            const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
            password.setAttribute('type', type);
            eyeIcon.classList.toggle('bi-eye-fill');
            eyeIcon.classList.toggle('bi-eye-slash-fill');
        });

        // NEW: Message Helper Function
        const showStatus = (type, title, message) => {
            const overlay = document.getElementById('statusOverlay');
            const circle = document.getElementById('statusCircle');
            const icon = document.getElementById('statusIcon');
            const titleEl = document.getElementById('statusTitle');
            const descEl = document.getElementById('statusDesc');

            // Reset classes
            circle.classList.remove('success', 'error');
            circle.classList.add(type);

            // Set content
            icon.className = type === 'success' ? 'bi bi-check-circle-fill' : 'bi bi-exclamation-circle-fill';
            titleEl.innerText = title;
            descEl.innerText = message;

            // Show
            overlay.style.display = 'flex';

            // Auto hide after 3 seconds
            setTimeout(() => {
                overlay.style.display = 'none';
            }, 3000);
        };

        // --- Remember Me Logic (LocalStorage) ---
        const loginForm = document.querySelector('#loginForm');
        const emailInput = document.querySelector('#emailInput');
        const rememberMeCheckbox = document.querySelector('#rememberMe');

        window.addEventListener('load', () => {
            const savedEmail = localStorage.getItem('shopeasy_email');
            if (savedEmail) {
                emailInput.value = savedEmail;
                rememberMeCheckbox.checked = true;
            }
        });

        loginForm.addEventListener('submit', (e) => {
            e.preventDefault(); // Prevents page refresh for demo
            
            if (rememberMeCheckbox.checked) {
                localStorage.setItem('shopeasy_email', emailInput.value);
            } else {
                localStorage.removeItem('shopeasy_email');
            }
            
            if (emailInput.value === 'admin@gmail.com' && passwordInput.value === '123456') {
                // Agar email aur password match hote hain
                showStatus('success', 'Success!', 'You are logged in successfully.');
            } else {
                // Agar galat credentials hain
                showStatus('error', 'Error!', 'Invalid email or password.');
            }
            
            console.log("Logging in...");
        });