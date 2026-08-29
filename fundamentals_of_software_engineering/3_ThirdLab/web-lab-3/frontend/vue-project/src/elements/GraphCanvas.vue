<script>
import { useHistoryStore } from '@/stores/HistoryStore.js'
import PopupWindow from '@/common/PopupWindow.vue'

export default {
  name: 'GraphCanvas',
  components: { PopupWindow },
  props: {
    x: Number,
    y: Number,
    r: Number,
    hit: { type: Number, default: -1 },
    size: { type: Number, default: 400 },
    scale: { type: Number, default: 40 }
  },
  emits: ['update:x', 'update:y'],
  data() {
    return {
      currentSize: this.size,
      currentScale: this.scale,
      errorMsg: '',
      errorTitle: ''
    }
  },
  watch: {
    x() { this.drawGraph() },
    y() { this.drawGraph() },
    r() { this.drawGraph() }
  },
  mounted() {
    this.updateResponsiveScale()
    window.addEventListener('resize', this.updateResponsiveScale)
    this.drawGraph()
  },
  beforeUnmount() {
    window.removeEventListener('resize', this.updateResponsiveScale)
  },
  methods: {
    updateResponsiveScale() {
      const width = window.innerWidth
      if (width < 400) {
        this.currentSize = 250
        this.currentScale = 25
      } else if (width < 600) {
        this.currentSize = 300
        this.currentScale = 30
      } else if (width < 900) {
        this.currentSize = 350
        this.currentScale = 35
      } else {
        this.currentSize = this.size
        this.currentScale = this.scale
      }
      this.$nextTick(() => this.drawGraph())
    },

  drawGraph() {
    const canvas = this.$refs.graph
    if (!canvas) return
    const ctx = canvas.getContext('2d')
    const width = this.currentSize
    const height = this.currentSize
    const centerX = width / 2
    const centerY = height / 2
    const s = this.currentScale

    ctx.clearRect(0, 0, width, height)

    // Axes
    ctx.beginPath()
    ctx.moveTo(0, centerY)
    ctx.lineTo(width, centerY)
    ctx.moveTo(centerX, 0)
    ctx.lineTo(centerX, height)
    ctx.strokeStyle = '#2c3e50'
    ctx.lineWidth = 1.5
    ctx.stroke()

    ctx.fillStyle = '#2c3e50'
    ctx.font = '12px Arial'
    for (let i = -5; i <= 5; i++) {
      if (i === 0) continue
      const xPos = centerX + i * s
      const yPos = centerY - i * s

      // X ticks
      ctx.beginPath()
      ctx.moveTo(xPos, centerY - 5)
      ctx.lineTo(xPos, centerY + 5)
      ctx.stroke()
      ctx.fillText(i.toString(), xPos - 4, centerY + 15)

      // Y ticks
      ctx.beginPath()
      ctx.moveTo(centerX - 5, yPos)
      ctx.lineTo(centerX + 5, yPos)
      ctx.stroke()
      ctx.fillText(i.toString(), centerX + 8, yPos + 4)
    }

    if (!this.r || this.r <= 0) return

    const Rpx = this.r * s
    ctx.fillStyle = 'rgba(52,152,219,0.4)'

    // Quarter circle
    ctx.beginPath()
    ctx.moveTo(centerX, centerY)
    ctx.arc(centerX, centerY, Rpx, 1.5 * Math.PI, 2 * Math.PI)
    ctx.closePath()
    ctx.fill()

    // Rectangle
    ctx.fillRect(centerX - Rpx / 2, centerY - Rpx, Rpx / 2, Rpx)

    // Triangle
    ctx.beginPath()
    ctx.moveTo(centerX, centerY)
    ctx.lineTo(centerX - Rpx / 2, centerY)
    ctx.lineTo(centerX, centerY + Rpx)
    ctx.closePath()
    ctx.fill()

    if (
      this.x !== null &&
      this.y !== null &&
      !isNaN(this.x) &&
      !isNaN(this.y)
    ) {
      const px = centerX + this.x * s
      const py = centerY - this.y * s
      ctx.beginPath()
      ctx.arc(px, py, 4, 0, Math.PI * 2)
      ctx.fillStyle = 'black'
      ctx.fill()
    }

    const historyStore = useHistoryStore()
    historyStore.history.forEach(pt => {
      if (this.r === pt.r) {
        const px = centerX + pt.x * s
        const py = centerY - pt.y * s
        ctx.beginPath()
        ctx.arc(px, py, 3, 0, Math.PI * 2)
        ctx.fillStyle = pt.hit ? 'hsl(140, 80%, 40%)' : 'red'
        ctx.fill()
      }
    })
  },

    handleCanvasClick(event) {
      if (!this.r) {
        this.errorTitle = 'График области не отрисован'
        this.errorMsg = 'Сначала выберите радиус R'
        return
      }
      const canvas = this.$refs.graph
      const rect = canvas.getBoundingClientRect()
      const centerX = this.currentSize / 2
      const centerY = this.currentSize / 2
      const s = this.currentScale
      const x = event.clientX - rect.left
      const y = event.clientY - rect.top
      const realX = (Math.round((x - centerX) / s) < 3 ? Math.round((x - centerX) / s) : 3)
      const realY = -((y - centerY) / s).toFixed(2) > -3 ? -((y - centerY) / s).toFixed(2) : -3
      this.$emit('update:x', realX)
      this.$emit('update:y', Number(realY))
    }
  }
}
</script>

<template>
  <PopupWindow
    v-if="errorMsg"
    :message="errorMsg"
    :title="errorTitle"
    @acknowledged="errorMsg=''"
  />
  <canvas
    ref="graph"
    :width="currentSize"
    :height="currentSize"
    class="graph-canvas"
    @click="handleCanvasClick"
  ></canvas>
</template>

<style scoped>
.graph-canvas {
  margin: 20px auto;
  display: block;
  border: 2px solid black;
  border-radius: 8px;
  background: #ffffff;
  max-width: 100%;
  height: auto;
  transition: all 0.3s ease;
}
.graph-canvas:hover {
  cursor: crosshair;
}
</style>
