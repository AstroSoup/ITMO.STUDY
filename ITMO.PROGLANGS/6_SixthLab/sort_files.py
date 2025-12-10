import os
import shutil

source_dir = os.path.expanduser("6_SixthLab/test_dir")

def sort_files_in_dir(directory):
    print(f"Анализ директории: {directory}...")
    
    for filename in os.listdir(directory):
        filepath = os.path.join(directory, filename)

        # Пропускаем вложенные директории
        if os.path.isdir(filepath):
            continue

        # Извлекаем расширение файла
        _, file_extension = os.path.splitext(filename)
        
        # Пропускаем файлы без расширения
        if not file_extension:
            continue
            
        # Нормализуем имя расширения (убираем точку, приводим к нижнему регистру)
        extension = file_extension[1:].lower()
        
        # Целевая директория для данного типа файлов
        target_folder = os.path.join(directory, extension)
        
        # Создаем целевую директорию, если она не существует
        if not os.path.exists(target_folder):
            os.makedirs(target_folder)
            print(f"Создана директория: {target_folder}")
            
        # Перемещаем файл
        try:
            shutil.move(filepath, os.path.join(target_folder, filename))
            print(f"Перемещен файл: {filename} -> {extension}/")
        except Exception as e:
            print(f"Ошибка при перемещении {filename}: {e}")

if __name__ == "__main__":
    # ВНИМАНИЕ: Скрипт выполняет реальное перемещение файлов.
    # Для безопасного тестирования рекомендуется создать отдельную папку.
    sort_files_in_dir(source_dir)
    print("Раскомментируйте вызов функции для запуска. Убедитесь, что 'source_dir' указан верно.")