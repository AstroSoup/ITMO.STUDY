<script>
export default {
  name: 'ComboBox',
  props: {
    modelValue: { type: [String, Number, Object], default: null },
    options: { type: Array, required: true },
    placeholder: { type: String, default: 'Select...' },
    label: { type: String, default: '' }, 
    disabled: { type: Boolean, default: false }
  },
  emits: ['update:modelValue'],
  data() {
    return {
      open: false,
      inputValue: '',
      highlightedIndex: -1
    }
  },
  computed: {
    normalizedOptions() {
      return this.options.map(o => {
        if (o && typeof o === 'object' && 'label' in o && 'value' in o) return o
        return { label: String(o), value: o }
      })
    },
    filteredOptions() {
      const input = this.inputValue.toLowerCase()
      return this.normalizedOptions.filter(o => o.label.toLowerCase().includes(input)).reverse()
    }
  },
  watch: {
    modelValue: {
      immediate: true,
      handler(val) {
        const found = this.normalizedOptions.find(o => o.value === val)
        this.inputValue = found ? found.label : val || ''
      }
    },
  },
  methods: {
    close() {
      this.open = false
      this.highlightedIndex = -1
    },
    onBlur() {
        setTimeout(() => {
          const found = this.normalizedOptions.find(
            o => o.label.toLowerCase() === this.inputValue.toLowerCase()
          )

          if (!found) {
            this.inputValue = ''
            this.$emit('update:modelValue', '')
          } else {
            this.inputValue = found.label
            this.$emit('update:modelValue', found.value)
          }
          this.close()
        }, 120)
    },
    onInput() {
      this.open = true
      this.highlightedIndex = 0
    },
    select(opt) {
      this.$emit('update:modelValue', opt.value)
      this.inputValue = opt.label
      this.close()
    },
    isSelected(opt) {
      return opt.value === this.modelValue
    },
    highlightNext() {
      if (!this.filteredOptions.length) return
      this.highlightedIndex = (this.highlightedIndex + 1) % this.filteredOptions.length
    },
    highlightPrev() {
      if (!this.filteredOptions.length) return
      this.highlightedIndex = (this.highlightedIndex - 1 + this.filteredOptions.length) % this.filteredOptions.length
    },
    selectHighlighted() {
      if (this.highlightedIndex >= 0 && this.highlightedIndex < this.filteredOptions.length) {
        this.select(this.filteredOptions[this.highlightedIndex])
      }
    }
  }
}
</script>

<template>
  <div class="combobox" @keydown.escape="close" tabindex="0">
    <label v-if="label" class="label-box">{{ label }}</label>

    <div class="input-wrapper">
      <input
        class="trigger"
        type="text"
        v-model="inputValue"
        @focus="open = true"
        @input="onInput"
        @keydown.enter.prevent="selectHighlighted"
        @keydown.arrow-down.prevent="highlightNext"
        @keydown.arrow-up.prevent="highlightPrev"
        @blur="onBlur"
        :placeholder="placeholder"
        :disabled="disabled"
      />

      <ul v-if="open && filteredOptions.length" class="list" role="listbox">
        <li
          v-for="(opt, idx) in filteredOptions"
          :key="idx"
          class="item"
          :class="{ highlighted: idx === highlightedIndex }"
          @mousedown.prevent="select(opt)"
          role="option"
          :aria-selected="isSelected(opt).toString()"
        >
          {{ opt.label }}
        </li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
.combobox {
  width: 90%;
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
}

.input-wrapper {
  flex: 1;
  position: relative;
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
}

.trigger:focus {
  box-shadow: 0 0 0 4px rgba(99, 102, 241, 0.12);
}

.trigger:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.list {
  margin: 8px 0 0 0;
  padding: 6px;
  list-style: none;
  position: absolute;
  left: 0;
  right: 0;
  background: #fff;
  border-radius: 10px;
  border: 1px solid rgba(0, 0, 0, 0.06);
  box-shadow: 0 12px 40px rgba(12, 16, 30, 0.08);
  max-height: 220px;
  overflow: auto;
  z-index: 50;
}

.item {
  padding: 8px 10px;
  border-radius: 8px;
  cursor: pointer;
}

.item.highlighted {
  background: rgba(99, 102, 241, 0.15);
}

.item:hover {
  background: rgba(0, 0, 0, 0.03);
}

.item[aria-selected="true"] {
  font-weight: 600;
  background: rgba(99, 102, 241, 0.06);
}
</style>
