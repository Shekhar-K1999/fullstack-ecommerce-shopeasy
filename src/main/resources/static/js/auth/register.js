

    // IMAGE PREVIEW

    const profileInput = document.getElementById('profileInput');
    const previewImg = document.getElementById('previewImg');
    const previewIcon = document.querySelector('#profilePreview i');

    profileInput.addEventListener('change', function () {

        const file = this.files[0];

        if (file) {

            const reader = new FileReader();

            reader.onload = function (e) {

                previewImg.src = e.target.result;
                previewImg.style.display = 'block';
                previewIcon.style.display = 'none';
            }

            reader.readAsDataURL(file);
        }
    });

    // PASSWORD TOGGLE

    function togglePass(inputId, iconId) {

        const input = document.getElementById(inputId);
        const icon = document.getElementById(iconId);

        if (input.type === "password") {

            input.type = "text";

            icon.classList.replace(
                "bi-eye-fill",
                "bi-eye-slash-fill"
            );

        } else {

            input.type = "password";

            icon.classList.replace(
                "bi-eye-slash-fill",
                "bi-eye-fill"
            );
        }
    }

    // STATUS POPUP

    const showStatus = (type, title, message) => {

        const overlay = document.getElementById('statusOverlay');
        const circle = document.getElementById('statusCircle');
        const icon = document.getElementById('statusIcon');
        const titleEl = document.getElementById('statusTitle');
        const descEl = document.getElementById('statusDesc');

        circle.classList.remove('success', 'error');

        circle.classList.add(type);

        icon.className =
            type === 'success'
                ? 'bi bi-check-circle-fill'
                : 'bi bi-exclamation-circle-fill';

        titleEl.innerText = title;
        descEl.innerText = message;

        overlay.style.display = 'flex';

        setTimeout(() => {
            overlay.style.display = 'none';
        }, 3000);
    };

    // REGISTER API

    document
        .getElementById('regForm')
        .addEventListener('submit', async function (e) {

            e.preventDefault();

            const fullname =
                document.getElementById('fullname').value.trim();

            const email =
                document.getElementById('email').value.trim();

            const phone =
                document.getElementById('phone').value.trim();

            const password =
                document.getElementById('pass1').value;

            const confirmPassword =
                document.getElementById('pass2').value;

            const imageFile =
                profileInput.files[0];

            const terms =
                document.getElementById('terms').checked;

            // VALIDATION

            if (
                !fullname ||
                !email ||
                !phone ||
                !password ||
                !confirmPassword
            ) {

                showStatus(
                    'error',
                    'Missing Fields',
                    'Please fill all required fields'
                );

                return;
            }

            if (!terms) {

                showStatus(
                    'error',
                    'Terms Required',
                    'Please accept terms & conditions'
                );

                return;
            }

            if (password !== confirmPassword) {

                showStatus(
                    'error',
                    'Password Error',
                    'Passwords do not match'
                );

                return;
            }

            // GENDER

            let gender = "OTHER";

            if (document.getElementById("m").checked) {
                gender = "MALE";
            }

            if (document.getElementById("f").checked) {
                gender = "FEMALE";
            }

            // FORM DATA

            const formData = new FormData();

            formData.append("fullname", fullname);
            formData.append("email", email);
            formData.append("phoneNumber", phone);
            formData.append("gender", gender);
            formData.append("password", password);
            formData.append("confirmPassword", confirmPassword);

            if (imageFile) {
                formData.append("profileImage", imageFile);
            }

            try {

                showStatus(
                    'success',
                    'Processing',
                    'Creating your account...'
                );

                const response = await fetch(
                    "http://localhost:8080/api/v1/auth/register",
                    {
                        method: "POST",
                        body: formData
                    }
                );

                const result = await response.json();

                if (response.ok && result.success) {

                    showStatus(
                        'success',
                        'Welcome!',
                        result.message
                    );

                    document
                        .getElementById('regForm')
                        .reset();

                    previewImg.style.display = 'none';
                    previewIcon.style.display = 'block';

                } else {

                    showStatus(
                        'error',
                        'Registration Failed',
                        result.message || 'Something went wrong'
                    );
                }

            } catch (error) {

                console.error(error);

                showStatus(
                    'error',
                    'Server Error',
                    'Backend connection failed'
                );
            }
        });

    // OAUTH2

    function googleLogin() {
        window.location.href =
            "http://localhost:8080/oauth2/authorization/google";
    }

    function facebookLogin() {
        window.location.href =
            "http://localhost:8080/oauth2/authorization/facebook";
    }