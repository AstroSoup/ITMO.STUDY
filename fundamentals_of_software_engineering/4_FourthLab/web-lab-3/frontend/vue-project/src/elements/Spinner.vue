<script>
export default {
  name: 'Spinner',
  props: {
    modelValue: { type: [String, Number, Object], default: null },
    placeholder: { type: String, default: 'Select...' },
    label: { type: String, default: '' }, 
    disabled: { type: Boolean, default: false }
  },
  data() {
    return {
      inputValue: '',
    }
  },
  emits: ['update:modelValue'],
  watch: {
    inputValue() {
        if (isNaN(Number(this.inputValue))) {
          this.inputValue = 0
        }
        if (this.inputValue > 5) {
          this.inputValue = 5
        }
        if (this.inputValue < -3) {
          this.inputValue = -3
        }
        this.$emit('update:modelValue', this.inputValue)
    },
    modelValue: {
      immediate: true,
      handler(val) {
        this.inputValue = val
      }
    }
  },
  methods: {
  onKeyPress(e) {
    const allowed = /[0-9\.\-]/;
    if (!allowed.test(e.key)) {
      e.preventDefault();
    }
  },
},
}
</script>

<template>
  <div class="spinner">
    <label v-if="label" class="label-box">{{ label }}</label>
    <div class="input-wrapper">
      <input
        class="trigger"
        type="number"
        step="0.01"
        v-model="inputValue"
        :placeholder="placeholder"
        :disabled="disabled"
        @keypress="onKeyPress"
      />
    </div>
  </div>
</template>


<style scoped>
.spinner {
  width: 97%; 
  font-family: Inter, system-ui, -apple-system, 'Segoe UI', Roboto, 'Helvetica Neue', Arial;
  position: relative;
  display: flex;
  align-items: stretch;
  flex: 1;
  min-width: 0;
}

.label-box {
  background: #00aabb;
  color: white;
  padding: 0 12px;
  display: flex;
  align-items: center;
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
  font-size: 0.9rem;
  font-weight: 500;
  white-space: nowrap;
  flex-shrink: 0;
}

.input-wrapper {
  flex: 1;
  position: relative;
  display: flex;
}

.trigger {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid rgba(0, 0, 0, 0.08);
  border-left: none;
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
  box-shadow: 0 6px 18px rgba(20, 20, 40, 0.04);
  outline: none;
  box-sizing: border-box;
}

.trigger:focus {
  box-shadow: 0 0 0 4px rgba(99, 102, 241, 0.12);
}

.trigger:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
</style>
