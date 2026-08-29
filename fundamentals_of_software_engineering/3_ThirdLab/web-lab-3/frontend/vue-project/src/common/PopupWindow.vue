<script>
export default {
  name: 'PopupWindow',
  props: {
    message: {
      type: String,
      required: true
    },
    title: {
        type: String,
        default: 'Уведомление'
    }
  },
  data() {
    return {
      visible: true
    };
  },
  methods: {
    acknowledge() {
      this.visible = false;
      this.$emit('acknowledged');
    }
  },
  mounted() {
    this.$nextTick(() => {
            if (this.visible && this.$refs.overlay) {
            this.$refs.overlay.focus()
            }
        })
    }
}
</script>

<template>
  <transition name="fade">
    <div v-if="visible" class="popup-overlay" tabindex="0" ref="overlay" @keydown.enter="acknowledge" @keydown.esc="acknowledge">
      <div class="popup-window">
        <h2>{{ title }}</h2>
        <p>{{ message }}</p>
        <button @click="acknowledge">Ок</button>
      </div>
    </div>
  </transition>
</template>

<style scoped>
/* Fade-in transition */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

/* Fullscreen overlay */
.popup-overlay {
  position: fixed;
  inset: 0;
  z-index: 9999; /* Ensures it's above everything */
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(0, 0, 0, 0.5);
  backdrop-filter: blur(4px); /* Adds soft blur */
}

/* Popup container */
.popup-window {
  background: #fff;
  border-radius: 16px;
  padding: 24px 32px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.2);
  width: 320px;
  max-width: 90%;
  text-align: center;
  animation: popup 0.25s ease-out;
}

/* Animation for popup scale */
@keyframes popup {
  from {
    opacity: 0;
    transform: scale(0.9);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

/* Text styles */
.popup-window h2 {
  margin: 0 0 12px;
  font-size: 20px;
  color: #222;
}

.popup-window p {
  color: #444;
  margin-bottom: 20px;
  line-height: 1.4;
}

/* Button styling */
.popup-window button {
  background-color: #2b6cb0;
  color: white;
  border: none;
  border-radius: 8px;
  padding: 10px 20px;
  font-size: 15px;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.popup-window button:hover {
  background-color: #2c5282;
}
</style>
