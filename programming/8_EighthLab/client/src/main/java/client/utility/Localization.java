package client.utility;

import java.util.HashMap;
import java.util.Map;

public class Localization {
    /** Supported languages. Each constant has a display name and a flag emoji. */
    public enum Language {
        RU("Русский", "🇷🇺"),
        BE("Беларуская", "🇧🇾"),
        LT("Lietuvių", "🇱🇹"),
        ES_GT("Español (Guatemala)", "🇬🇹");

        private final String displayName;
        private final String flagEmoji;

        Language(String displayName, String flagEmoji) {
            this.displayName = displayName;
            this.flagEmoji = flagEmoji;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getFlagEmoji() {
            return flagEmoji;
        }
    }

    /** The “currently selected” language. Default = Russian. */
    private static Language currentLanguage = Language.RU;

    /**
     * A map from language → ( map of key → translated text ).
     * We fill it in a static block.
     */
    private static final Map<Language, Map<String, String>> resources = new HashMap<>();

    static {
        // For each language, create and fill a sub‐map of (key → translation).
        // --- RUSSIAN ---
        Map<String, String> ru = new HashMap<>();
        ru.put("frame.title", "Главная панель");
        ru.put("label.logged_in_as", "Вы вошли как: %s"); // %s → username
        ru.put("commands.title", "Команды");
        ru.put("placeholder", "Будущая команда");
        ru.put("label.filter_by", "Фильтровать по:");
        ru.put("apply_filter", "Применить фильтр");
        ru.put("clear_filter", "Сбросить фильтр");
        ru.put("add_ticket", "Добавить билет");
        ru.put("remove_ticket", "Удалить билет");
        ru.put("update_ticket", "Обновить билет");
        ru.put("table.border", "Таблица данных");
        ru.put("animated.border", "Визуализация коллекции");

        // Remove dialog / messages (Russian)
        ru.put("dialog.remove.title", "Удалить билет");
        ru.put("dialog.remove.prompt", "Введите ID билета для удаления:");
        ru.put("message.id_empty", "ID не может быть пустым.");
        ru.put("message.error", "Ошибка");
        ru.put("message.not_creator", "Билет с данным ID принадлежит другому пользователю.");
        ru.put("message.success.remove", "Билет удален из коллекции.");
        ru.put("message.not_in_collection", "Билет с данным ID отсутствует в коллекции.");
        ru.put("message.invalid_id_format", "Неверный формат ID. Должно быть числом.");

        // Add dialog:
        ru.put("dialog.add.title", "Добавить новый билет");
        ru.put("label.name", "Имя:");
        ru.put("label.price", "Цена:");
        ru.put("label.discount", "Скидка:");
        ru.put("label.type", "Тип:");
        ru.put("text.none", "Нет");
        ru.put("label.coord_x", "Координата X:");
        ru.put("label.coord_y", "Координата Y:");
        ru.put("label.passport", "Паспортные данные:");
        ru.put("label.eye_color", "Цвет глаз:");
        ru.put("label.loc_x", "Локация X:");
        ru.put("label.loc_y", "Локация Y:");
        ru.put("label.loc_z", "Локация Z:");
        ru.put("message.all_fields", "Все поля, кроме «Тип», должны быть заполнены.");
        ru.put("message.invalid_name", "Неверное имя.");
        ru.put("message.invalid_price", "Цена должна быть целым числом > 0 и < " + Long.MAX_VALUE + ".");
        ru.put("message.invalid_discount", "Неверное значение. Скидка: от 0 до 100.");
        ru.put("message.invalid_coord_x", "Неверный аргумент. X от " + (-Float.MAX_VALUE) + " до 952.");
        ru.put("message.invalid_coord_y", "Неверный аргумент. Y от " + (-Double.MAX_VALUE) + " до " + Double.MAX_VALUE + ".");
        ru.put("message.invalid_passport", "Неверный формат паспорта. От 6 до 28 символов.");
        ru.put("message.numeric_invalid", "Числовые поля имеют неверный формат. Проверьте ввод.");

        // Update step 1:
        ru.put("dialog.update.step1", "Обновить билет – Шаг 1");
        ru.put("dialog.update.prompt.id", "Введите ID билета для обновления:");
        ru.put("dialog.update.id_not_found", "ID отсутствует в коллекции.");
        ru.put("dialog.update.title", "Обновить билет (ID = %d)");
        ru.put("col.id", "ID");
        ru.put("col.name", "Имя");
        ru.put("col.price", "Цена");
        ru.put("col.discount", "Скидка");
        ru.put("col.type", "Тип");
        ru.put("col.coord_x", "Коорд. X");
        ru.put("col.coord_y", "Коорд. Y");
        ru.put("col.passport", "Паспортные данные");
        ru.put("col.eye_color", "Цвет глаз");
        ru.put("col.loc_x", "Локация X");
        ru.put("col.loc_y", "Локация Y");
        ru.put("col.loc_z", "Локация Z");
        ru.put("col.creation_date", "Дата создания");
        ru.put("col.creator", "Создатель");
        ru.put("info.id", "ID: ");
        ru.put("info.name", "Имя: ");
        ru.put("info.price", "Цена: ");
        ru.put("info.discount", "Скидка: ");
        ru.put("info.type", "Тип: ");
        ru.put("info.coord_x", "Коорд. X: ");
        ru.put("info.coord_y", "Коорд. Y: ");
        ru.put("info.passport", "Паспортные данные: ");
        ru.put("info.eye_color", "Цвет глаз: ");
        ru.put("info.loc_x", "Локация X: ");
        ru.put("info.loc_y", "Локация Y: ");
        ru.put("info.loc_z", "Локация Z: ");
        ru.put("info.creation_date", "Дата создания: ");
        ru.put("info.creator", "Создатель: ");

        ru.put("auth.title",                 "Авторизация / Регистрация");
        ru.put("tab.login",                  "Вход");
        ru.put("tab.register",               "Регистрация");

        ru.put("label.username",             "Имя пользователя:");
        ru.put("label.password",             "Пароль:");
        ru.put("label.confirm_password",     "Подтвердите пароль:");

        ru.put("button.login",               "Войти");
        ru.put("button.register",            "Зарегистрироваться");

        ru.put("msg.enter_both",             "Пожалуйста, введите имя пользователя и пароль.");
        ru.put("title.missing_info",         "Недостающая информация");

        ru.put("msg.invalid_credentials",    "Неверное имя пользователя или пароль.");
        ru.put("title.auth_failed",          "Ошибка авторизации");

        ru.put("msg.fill_all_fields",        "Пожалуйста, заполните все поля.");
        ru.put("msg.passwords_not_match",    "Пароли не совпадают.");
        ru.put("title.validation_error",     "Ошибка валидации");

        ru.put("msg.registration_success",   "Регистрация прошла успешно! Теперь вы можете войти.");
        ru.put("title.success",              "Успех");

        ru.put("msg.registration_failed",    "Ошибка регистрации. Возможно, имя пользователя уже существует.");
        ru.put("title.error",                "Ошибка");
        
        // Update labels (reuse add‐dialog labels for fields)
        // Fallback for “Invalid Name” etc. use the same keys as in Add.

        // --- BELARUSIAN ---
        Map<String, String> be = new HashMap<>();
        be.put("frame.title", "Галоўная панэль");
        be.put("label.logged_in_as", "Увайшоў як: %s");
        be.put("commands.title", "Каманды");
        be.put("placeholder", "Будучая каманда");
        be.put("label.filter_by", "Фільтраваць па:");
        be.put("apply_filter", "Прымяніць фільтр");
        be.put("clear_filter", "Скінуць фільтр");
        be.put("add_ticket", "Дадаць білет");
        be.put("remove_ticket", "Выдаліць білет");
        be.put("update_ticket", "Абнавіць білет");
        be.put("table.border", "Табліца даных");
        be.put("animated.border", "Візуалізацыя калекцыі");

        be.put("dialog.remove.title", "Выдаліць білет");
        be.put("dialog.remove.prompt", "Увядзіце ID білета для выдалення:");
        be.put("message.id_empty", "ІD не можа быць пустым.");
        be.put("message.error", "Памылка");
        be.put("message.not_creator", "Білет з гэтым ID належыць іншаму карыстальніку.");
        be.put("message.success.remove", "Білет выдалены з калекцыі.");
        be.put("message.not_in_collection", "Білета з гэтым ID няма ў калекцыі.");
        be.put("message.invalid_id_format", "Няправільны фармат ID. Павінна быць лікам.");

        be.put("dialog.add.title", "Дадаць новы білет");
        be.put("label.name", "Імя:");
        be.put("label.price", "Кошт:");
        be.put("label.discount", "Зніжка:");
        be.put("label.type", "Тып:");
        be.put("text.none", "Няма");
        be.put("label.coord_x", "Каардыната X:");
        be.put("label.coord_y", "Каардыната Y:");
        be.put("label.passport", "Пашпарт ID:");
        be.put("label.eye_color", "Колер вачэй:");
        be.put("label.loc_x", "Лакацыя X:");
        be.put("label.loc_y", "Лакацыя Y:");
        be.put("label.loc_z", "Лакацыя Z:");
        be.put("message.all_fields", "Усе палі, акрамя «Тып», павінны быць запоўнены.");
        be.put("message.invalid_name", "Няправільнае імя.");
        be.put("message.invalid_price", "Кошт павінен быць цэлым > 0 і < " + Long.MAX_VALUE + ".");
        be.put("message.invalid_discount", "Няправільнае значэнне. Зніжка: ад 0 да 100.");
        be.put("message.invalid_coord_x", "Няправільны аргумент. X ад " + (-Float.MAX_VALUE) + " да 952.");
        be.put("message.invalid_coord_y", "Няправільны аргумент. Y ад " + (-Double.MAX_VALUE) + " да " + Double.MAX_VALUE + ".");
        be.put("message.invalid_passport", "Няправільны фармат пашпарта: ад 6 да 28 сімвалаў.");
        be.put("message.numeric_invalid", "Лічбавыя палі маюць няправільны фармат. Праверце ўвод.");

        be.put("dialog.update.step1", "Абнавіць білет – Крок 1");
        be.put("dialog.update.prompt.id", "Увядзіце ID білета для абнаўлення:");
        be.put("dialog.update.id_not_found", "ID адсутнічае ў калекцыі.");
        be.put("dialog.update.title", "Абнавіць білет (ID = %d)");

        be.put("col.id", "ID");
        be.put("col.name", "Імя");
        be.put("col.price", "Кошт");
        be.put("col.discount", "Зніжка");
        be.put("col.type", "Тып");
        be.put("col.coord_x", "Каард. X");
        be.put("col.coord_y", "Каард. Y");
        be.put("col.passport", "Пашпарт ID");
        be.put("col.eye_color", "Колер вачэй");
        be.put("col.loc_x", "Лакацыя X");
        be.put("col.loc_y", "Лакацыя Y");
        be.put("col.loc_z", "Лакацыя Z");
        be.put("col.creation_date", "Дата стварэння");
        be.put("col.creator", "Стварыльнік");

        be.put("info.id", "ID: ");
        be.put("info.name", "Імя: ");
        be.put("info.price", "Кошт: ");
        be.put("info.discount", "Зніжка: ");
        be.put("info.type", "Тып: ");
        be.put("info.coord_x", "Каард. X: ");
        be.put("info.coord_y", "Каард. Y: ");
        be.put("info.passport", "Пашпарт ID: ");
        be.put("info.eye_color", "Колер вачэй: ");
        be.put("info.loc_x", "Лакацыя X: ");
        be.put("info.loc_y", "Лакацыя Y: ");
        be.put("info.loc_z", "Лакацыя Z: ");
        be.put("info.creation_date", "Дата стварэння: ");
        be.put("info.creator", "Стварыльнік: ");
        be.put("auth.title",                 "Аўтарызацыя / Рэгістрацыя");
        be.put("tab.login",                  "Увад");
        be.put("tab.register",               "Рэгістрацыя");

        be.put("label.username",             "Імя карыстальніка:");
        be.put("label.password",             "Пароль:");
        be.put("label.confirm_password",     "Пацвердзіце пароль:");

        be.put("button.login",               "Увайсці");
        be.put("button.register",            "Зарэгістравацца");

        be.put("msg.enter_both",             "Калі ласка, увядзіце імя карыстальніка і пароль.");
        be.put("title.missing_info",         "Недахоп інфармацыі");

        be.put("msg.invalid_credentials",    "Няправільнае імя карыстальніка або пароль.");
        be.put("title.auth_failed",          "Памылка аўтарызацыі");

        be.put("msg.fill_all_fields",        "Калі ласка, запоўніце ўсе палі.");
        be.put("msg.passwords_not_match",    "Паролі не супадаюць.");
        be.put("title.validation_error",     "Памылка праверкі");

        be.put("msg.registration_success",   "Рэгістрацыя прайшла паспяхова! Цяпер можна ўвайсці.");
        be.put("title.success",              "Паспяхова");

        be.put("msg.registration_failed",    "Памылка рэгістрацыі. Магчыма, імя карыстальніка ўжо існуе.");
        be.put("title.error",                "Памылка");


        // --- LITHUANIAN ---
        Map<String, String> lt = new HashMap<>();
        lt.put("frame.title", "Pagrindinis skydelis");
        lt.put("label.logged_in_as", "Prisijungęs kaip: %s");
        lt.put("commands.title", "Komandos");
        lt.put("placeholder", "Būsima komanda");
        lt.put("label.filter_by", "Filtruoti pagal:");
        lt.put("apply_filter", "Taikyti filtrą");
        lt.put("clear_filter", "Išvalyti filtrą");
        lt.put("add_ticket", "Pridėti bilietą");
        lt.put("remove_ticket", "Pašalinti bilietą");
        lt.put("update_ticket", "Atnaujinti bilietą");
        lt.put("table.border", "Duomenų lentelė");
        lt.put("animated.border", "Kolekcijos vizualizacija");

        lt.put("dialog.remove.title", "Pašalinti bilietą");
        lt.put("dialog.remove.prompt", "Įveskite pašalinkite bilieto ID:");
        lt.put("message.id_empty", "ID negali būti tuščias.");
        lt.put("message.error", "Klaida");
        lt.put("message.not_creator", "Bilietas su šiuo ID priklauso kitam vartotojui.");
        lt.put("message.success.remove", "Bilietas pašalintas iš kolekcijos.");
        lt.put("message.not_in_collection", "Bilieto su šiuo ID nėra kolekcijoje.");
        lt.put("message.invalid_id_format", "Neteisingas ID formatas. Turi būti skaičius.");

        lt.put("dialog.add.title", "Pridėti naują bilietą");
        lt.put("label.name", "Pavadinimas:");
        lt.put("label.price", "Kaina:");
        lt.put("label.discount", "Nuolaida:");
        lt.put("label.type", "Tipas:");
        lt.put("text.none", "Nėra");
        lt.put("label.coord_x", "Koordinatė X:");
        lt.put("label.coord_y", "Koordinatė Y:");
        lt.put("label.passport", "Paso ID:");
        lt.put("label.eye_color", "Akių spalva:");
        lt.put("label.loc_x", "Vieta X:");
        lt.put("label.loc_y", "Vieta Y:");
        lt.put("label.loc_z", "Vieta Z:");
        lt.put("message.all_fields", "Visi laukai, išskyrus „Tipą“, turi būti užpildyti.");
        lt.put("message.invalid_name", "Neteisingas pavadinimas.");
        lt.put("message.invalid_price", "Kaina turi būti sveikasis skaičius > 0 ir < " + Long.MAX_VALUE + ".");
        lt.put("message.invalid_discount", "Neteisinga vertė. Nuolaida nuo 0 iki 100.");
        lt.put("message.invalid_coord_x", "Neteisingas argumentas. X nuo " + (-Float.MAX_VALUE) + " iki 952.");
        lt.put("message.invalid_coord_y", "Neteisingas argumentas. Y nuo " + (-Double.MAX_VALUE) + " iki " + Double.MAX_VALUE + ".");
        lt.put("message.invalid_passport", "Klaidingas paso formatas: nuo 6 iki 28 simbolių.");
        lt.put("message.numeric_invalid", "Skaitiniai laukai turi neteisingą formatą. Patikrinkite įvestį.");

        lt.put("dialog.update.step1", "Atnaujinti bilietą – 1 žingsnis");
        lt.put("dialog.update.prompt.id", "Įveskite atnaujinti bilieto ID:");
        lt.put("dialog.update.id_not_found", "ID nerastas kolekcijoje.");
        lt.put("dialog.update.title", "Atnaujinti bilietą (ID = %d)");

        lt.put("col.id", "ID");
        lt.put("col.name", "Pavadinimas");
        lt.put("col.price", "Kaina");
        lt.put("col.discount", "Nuolaida");
        lt.put("col.type", "Tipas");
        lt.put("col.coord_x", "Koord. X");
        lt.put("col.coord_y", "Koord. Y");
        lt.put("col.passport", "Paso ID");
        lt.put("col.eye_color", "Akių spalva");
        lt.put("col.loc_x", "Vieta X");
        lt.put("col.loc_y", "Vieta Y");
        lt.put("col.loc_z", "Vieta Z");
        lt.put("col.creation_date", "Sukūrimo data");
        lt.put("col.creator", "Kūrėjas");

        // === TICKET INFO DIALOG LABELS ===
        lt.put("info.id", "ID: ");
        lt.put("info.name", "Pavadinimas: ");
        lt.put("info.price", "Kaina: ");
        lt.put("info.discount", "Nuolaida: ");
        lt.put("info.type", "Tipas: ");
        lt.put("info.coord_x", "Koord. X: ");
        lt.put("info.coord_y", "Koord. Y: ");
        lt.put("info.passport", "Paso ID: ");
        lt.put("info.eye_color", "Akių spalva: ");
        lt.put("info.loc_x", "Vieta X: ");
        lt.put("info.loc_y", "Vieta Y: ");
        lt.put("info.loc_z", "Vieta Z: ");
        lt.put("info.creation_date", "Sukūrimo data: ");
        lt.put("info.creator", "Kūrėjas: ");

        lt.put("auth.title",                 "Autentifikacija / Registracija");
        lt.put("tab.login",                  "Prisijungti");
        lt.put("tab.register",               "Registruotis");

        lt.put("label.username",             "Vartotojo vardas:");
        lt.put("label.password",             "Slaptažodis:");
        lt.put("label.confirm_password",     "Patvirtinkite slaptažodį:");

        lt.put("button.login",               "Prisijungti");
        lt.put("button.register",            "Registruotis");

        lt.put("msg.enter_both",             "Prašome įvesti vartotojo vardą ir slaptažodį.");
        lt.put("title.missing_info",         "Trūksta informacijos");

        lt.put("msg.invalid_credentials",    "Neteisingas vartotojo vardas arba slaptažodis.");
        lt.put("title.auth_failed",          "Prisijungimo klaida");

        lt.put("msg.fill_all_fields",        "Prašome užpildyti visus laukus.");
        lt.put("msg.passwords_not_match",    "Slaptažodžiai nesutampa.");
        lt.put("title.validation_error",     "Validacijos klaida");

        lt.put("msg.registration_success",   "Registracija sėkminga! Dabar galite prisijungti.");
        lt.put("title.success",              "Sėkmė");

        lt.put("msg.registration_failed",    "Registracija nepavyko. Vartotojo vardas gali būti užimtas.");
        lt.put("title.error",                "Klaida");


        // --- SPANISH (GUATEMALA) ---
        Map<String, String> esGt = new HashMap<>();
        esGt.put("frame.title", "Panel Principal");
        esGt.put("label.logged_in_as", "Conectado como: %s");
        esGt.put("commands.title", "Comandos");
        esGt.put("placeholder", "Comando futuro");
        esGt.put("label.filter_by", "Filtrar por:");
        esGt.put("apply_filter", "Aplicar filtro");
        esGt.put("clear_filter", "Borrar filtro");
        esGt.put("add_ticket", "Agregar boleto");
        esGt.put("remove_ticket", "Eliminar boleto");
        esGt.put("update_ticket", "Actualizar boleto");
        esGt.put("table.border", "Tabla de datos");
        esGt.put("animated.border", "Visualización de la colección");

        esGt.put("dialog.remove.title", "Eliminar boleto");
        esGt.put("dialog.remove.prompt", "Ingrese el ID del boleto a eliminar:");
        esGt.put("message.id_empty", "El ID no puede estar vacío.");
        esGt.put("message.error", "Error");
        esGt.put("message.not_creator", "El boleto con este ID pertenece a otro usuario.");
        esGt.put("message.success.remove", "Boleto eliminado de la colección.");
        esGt.put("message.not_in_collection", "El boleto con este ID no existe en la colección.");
        esGt.put("message.invalid_id_format", "Formato de ID no válido. Debe ser un número.");

        esGt.put("dialog.add.title", "Agregar nuevo boleto");
        esGt.put("label.name", "Nombre:");
        esGt.put("label.price", "Precio:");
        esGt.put("label.discount", "Descuento:");
        esGt.put("label.type", "Tipo:");
        esGt.put("text.none", "Ninguno");
        esGt.put("label.coord_x", "Coordenada X:");
        esGt.put("label.coord_y", "Coordenada Y:");
        esGt.put("label.passport", "ID Pasaporte:");
        esGt.put("label.eye_color", "Color de ojos:");
        esGt.put("label.loc_x", "Ubicación X:");
        esGt.put("label.loc_y", "Ubicación Y:");
        esGt.put("label.loc_z", "Ubicación Z:");
        esGt.put("message.all_fields", "Todos los campos excepto «Tipo» deben completarse.");
        esGt.put("message.invalid_name", "Nombre inválido.");
        esGt.put("message.invalid_price", "El precio debe ser un entero > 0 y < " + Long.MAX_VALUE + ".");
        esGt.put("message.invalid_discount", "Argumento inválido. Descuento: de 0 a 100.");
        esGt.put("message.invalid_coord_x", "Argumento inválido. X de " + (-Float.MAX_VALUE) + " a 952.");
        esGt.put("message.invalid_coord_y", "Argumento inválido. Y de " + (-Double.MAX_VALUE) + " a " + Double.MAX_VALUE + ".");
        esGt.put("message.invalid_passport", "Formato de pasaporte inválido: de 6 a 28 caracteres.");
        esGt.put("message.numeric_invalid", "Campos numéricos con formato inválido. Verifique sus datos.");

        esGt.put("dialog.update.step1", "Actualizar boleto – Paso 1");
        esGt.put("dialog.update.prompt.id", "Ingrese el ID del boleto a actualizar:");
        esGt.put("dialog.update.id_not_found", "ID no encontrado en la colección.");
        esGt.put("dialog.update.title", "Actualizar boleto (ID = %d)");
        esGt.put("col.id", "ID");
        esGt.put("col.name", "Nombre");
        esGt.put("col.price", "Precio");
        esGt.put("col.discount", "Descuento");
        esGt.put("col.type", "Tipo");
        esGt.put("col.coord_x", "Coord. X");
        esGt.put("col.coord_y", "Coord. Y");
        esGt.put("col.passport", "Pasaporte ID");
        esGt.put("col.eye_color", "Color de ojos");
        esGt.put("col.loc_x", "Ubicación X");
        esGt.put("col.loc_y", "Ubicación Y");
        esGt.put("col.loc_z", "Ubicación Z");
        esGt.put("col.creation_date", "Fecha creación");
        esGt.put("col.creator", "Creador");

        // === TICKET INFO DIALOG LABELS ===
        esGt.put("info.id", "ID: ");
        esGt.put("info.name", "Nombre: ");
        esGt.put("info.price", "Precio: ");
        esGt.put("info.discount", "Descuento: ");
        esGt.put("info.type", "Tipo: ");
        esGt.put("info.coord_x", "Coord. X: ");
        esGt.put("info.coord_y", "Coord. Y: ");
        esGt.put("info.passport", "Pasaporte ID: ");
        esGt.put("info.eye_color", "Color de ojos: ");
        esGt.put("info.loc_x", "Ubicación X: ");
        esGt.put("info.loc_y", "Ubicación Y: ");
        esGt.put("info.loc_z", "Ubicación Z: ");
        esGt.put("info.creation_date", "Fecha creación: ");
        esGt.put("info.creator", "Creador: ");

        esGt.put("auth.title",                 "Autenticación / Registro");
        esGt.put("tab.login",                  "Iniciar Sesión");
        esGt.put("tab.register",               "Registrarse");

        esGt.put("label.username",             "Nombre de usuario:");
        esGt.put("label.password",             "Contraseña:");
        esGt.put("label.confirm_password",     "Confirmar contraseña:");

        esGt.put("button.login",               "Iniciar Sesión");
        esGt.put("button.register",            "Registrarse");

        esGt.put("msg.enter_both",             "Por favor ingrese nombre de usuario y contraseña.");
        esGt.put("title.missing_info",         "Falta Información");

        esGt.put("msg.invalid_credentials",    "Nombre de usuario o contraseña inválidos.");
        esGt.put("title.auth_failed",          "Autenticación Fallida");

        esGt.put("msg.fill_all_fields",        "Por favor complete todos los campos.");
        esGt.put("msg.passwords_not_match",    "Las contraseñas no coinciden.");
        esGt.put("title.validation_error",     "Error de Validación");

        esGt.put("msg.registration_success",   "¡Registro exitoso! Ya puede iniciar sesión.");
        esGt.put("title.success",              "Éxito");

        esGt.put("msg.registration_failed",    "Error en el registro. El nombre de usuario puede ya existir.");
        esGt.put("title.error",                "Error");



        // Finally, put all sub‐maps into the main `resources` map:
        resources.put(Language.RU, ru);
        resources.put(Language.BE, be);
        resources.put(Language.LT, lt);
        resources.put(Language.ES_GT, esGt);
    }

    /** Change the current language at runtime. */
    public static void setLanguage(Language lang) {
        if (lang == null) return;
        currentLanguage = lang;
    }

    /** Get the currently selected language. */
    public static Language getLanguage() {
        return currentLanguage;
    }

    /**
     * Fetches the localized text for a given key. If the key is missing,
     * returns the key itself (as a fallback).
     */
    public static String get(String key) {
        Map<String, String> map = resources.get(currentLanguage);
        if (map != null && map.containsKey(key)) {
            return map.get(key);
        }
        // Fallback: return key itself
        return key;
    }

    /**
     * A convenience method to do String.format() on a localized string
     * that includes format specifiers like "%s" or "%d".
     */
    public static String format(String key, Object... args) {
        String pattern = get(key);
        try {
            return String.format(pattern, args);
        } catch (Exception e) {
            // If formatting fails, just append args
            StringBuilder sb = new StringBuilder(pattern);
            for (Object o : args) {
                sb.append(" ").append(o);
            }
            return sb.toString();
        }
    }
}

