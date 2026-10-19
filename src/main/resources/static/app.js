(function () {
    "use strict";

    const STORAGE_KEY = "hermes-fable-started-at";
    const claimButton = document.getElementById("claim-button");
    const claimStatus = document.getElementById("claim-status");
    const timerPanel = document.getElementById("timer-panel");
    const timerMessage = document.getElementById("timer-message");
    const hoursNode = document.getElementById("timer-hours");
    const minutesNode = document.getElementById("timer-minutes");
    const secondsNode = document.getElementById("timer-seconds");
    const resetForm = document.getElementById("timer-reset-form");
    const resetPassword = document.getElementById("reset-password");
    const resetStatus = document.getElementById("reset-status");
    const resetPasswordValue = "luzzitotoso";
    const durationMs = 48 * 60 * 60 * 1000;
    let startedAt = Number(window.localStorage.getItem(STORAGE_KEY));

    function revealRelics() {
        document.getElementById("silver-card").classList.add("is-revealed");
        document.getElementById("silver-card").classList.remove("is-locked");
        document.getElementById("gold-card").classList.add("is-revealed");
        document.getElementById("gold-card").classList.remove("is-locked");
    }

    function updateTimer() {
        if (!Number.isFinite(startedAt) || startedAt <= 0) {
            return;
        }

        timerPanel.hidden = false;
        revealRelics();
        claimButton.disabled = true;
        claimButton.textContent = "Relíquias reveladas";

        const remainingMs = Math.max(0, startedAt + durationMs - Date.now());
        const remainingSeconds = Math.floor(remainingMs / 1000);
        const hours = Math.floor(remainingSeconds / 3600);
        const minutes = Math.floor((remainingSeconds % 3600) / 60);
        const seconds = remainingSeconds % 60;

        hoursNode.textContent = String(hours).padStart(2, "0");
        minutesNode.textContent = String(minutes).padStart(2, "0");
        secondsNode.textContent = String(seconds).padStart(2, "0");
        timerMessage.textContent = "Tempo restante para explorar esta experiência.";

        if (remainingMs === 0) {
            timerMessage.textContent = "A janela demonstrativa terminou. Nenhum saldo ou valor real foi afetado.";
            claimStatus.textContent = "A fábula foi concluída neste navegador.";
        }
    }

    if (Number.isFinite(startedAt) && startedAt > 0) {
        claimStatus.textContent = "Experiência iniciada neste navegador; não representa um ativo real.";
        updateTimer();
        window.setInterval(updateTimer, 1000);
    }

    claimButton.addEventListener("click", function () {
        if (Number.isFinite(startedAt) && startedAt > 0) {
            return;
        }

        startedAt = Date.now();
        window.localStorage.setItem(STORAGE_KEY, String(startedAt));
        claimStatus.textContent = "Relíquias reveladas. A contagem é apenas demonstrativa e local.";
        updateTimer();
        window.setInterval(updateTimer, 1000);
    });

    resetForm.addEventListener("submit", function (event) {
        event.preventDefault();

        if (resetPassword.value !== resetPasswordValue) {
            resetStatus.textContent = "Senha incorreta. O contador não foi alterado.";
            resetPassword.value = "";
            resetPassword.focus();
            return;
        }

        startedAt = Date.now();
        window.localStorage.setItem(STORAGE_KEY, String(startedAt));
        resetPassword.value = "";
        resetStatus.textContent = "Contador reiniciado para 48 horas neste navegador.";
        claimStatus.textContent = "Contador reiniciado. Esta experiência continua sendo fictícia.";
        updateTimer();
    });
}());
