<script>
export default {
  name: 'FooterBar'
}
</script>

<template>
  <footer class="footer">
    <div class="footer-inner">
      <div class="contacts">
        <div class="links">
          <a href="https://t.me/astro_soup" target="_blank" rel="noopener">
            <img
              src="https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white"
              alt="Telegram"
            />
          </a>
          <a href="https://vk.com/astro_soup" target="_blank" rel="noopener">
            <img
              src="https://img.shields.io/badge/ВКонтакте-%232E87FB.svg?&style=for-the-badge&logo=vk&logoColor=white"
              alt="VK"
            />
          </a>
          <a href="https://discord.com/users/697719405748158465" target="_blank" rel="noopener">
            <img
              src="https://img.shields.io/badge/Discord-7289DA?style=for-the-badge&logo=discord&logoColor=white"
              alt="Discord"
            />
          </a>
          <a href="mailto:astro_soup@niuitmo.ru">
            <img
              src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white"
              alt="Gmail"
            />
          </a>
        </div>

        <img src="@/assets/tsukasa-phone.gif" class="tsukasa" alt="Tsukasa animation" />
      </div>
    </div>
  </footer>
</template>

<style scoped>
.footer {
  background: linear-gradient(90deg, violet, indigo, blue, green, yellow, orange, red);
  padding: 14px;
  color: white;
}

.footer-inner {
  max-width: var(--max-width);
  margin: 0 auto;
  width: 100%;
}


.contacts {
  display: flex;
  flex-direction: column; 
  align-items: center;
  justify-content: center;
  gap: 12px;
  text-align: center;
}

.links {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 10px;
}

.links img {
  height: 35px;
  transition: transform 0.2s;
}

.links img:hover {
  transform: scale(1.05);
}

.tsukasa {
  height: 70px;
  border-radius: 10px;
  object-fit: contain;
  margin-top: 6px;
}



/* Tablet (885–1118px)*/
@media (min-width: 885px) and (max-width: 1118px) {
  .tsukasa {
    height: 65px;
  }
}

/* Mobile (<885px) */
@media (max-width: 884px) {
  .links {
    gap: 8px;
  }

  .links img {
    height: 32px;
  }

  .tsukasa {
    height: 55px;
  }
}
</style>
