# UML Page Object для GreenCity

Схеми відповідають [архітектурі](ui-page-object-architecture.md). Приватні `@FindBy` на діаграмах немає: поле класу — це інший page object, з яким сторінка працює. Дії користувача лежать на класі, у якого цей контрол на екрані.

Як читати стрілки:

| Стрілка | Значення |
| --- | --- |
| `<\|--` | наслідування |
| `*--` | сторінка або контейнер володіє частиною |
| `-->` | метод створює і повертає інший об'єкт |

`Base` уже містить спільні дії WebDriver (`scrollToElement`, `waitUntilElementVisible`, `waitUntilElementClickable`, `refreshPage`). На схемах нижче вони не повторюються.

## Базові класи

```mermaid
classDiagram
  class Base {
    #driver WebDriver
    #wait WebDriverWait
    +scrollToElement(element) void
    +waitUntilElementVisible(element) void
    +waitUntilElementClickable(element) void
    +refreshPage() void
  }
  class BasePage {
    +header HeaderComponent
    +waitForPageToLoad(timeout) void
  }
  class GreenCityPage {
    +footer FooterComponent
    +isLoaded() boolean
  }
  class BaseComponent {
    #rootElement WebElement
    +isDisplayed() boolean
  }
  class BaseModal {
    +close() void
    +isOpen() boolean
  }
  class BaseElement {
    #rootElement WebElement
  }
  Base <|-- BasePage
  Base <|-- BaseComponent
  Base <|-- BaseElement
  BasePage <|-- GreenCityPage
  BasePage <|-- ConfirmRestorePasswordPage
  BaseComponent <|-- BaseModal
  GreenCityPage <|-- HomePage
  GreenCityPage <|-- AboutPage
  GreenCityPage <|-- NewsListPage
  GreenCityPage <|-- NewsDetailPage
  GreenCityPage <|-- CreateEditNewsPage
  GreenCityPage <|-- EventsListPage
  GreenCityPage <|-- EventDetailsPage
  GreenCityPage <|-- EventEditorPage
  GreenCityPage <|-- PlacesPage
  GreenCityPage <|-- SearchResultsPage
  GreenCityPage <|-- ProfilePage
  GreenCityPage <|-- EditProfilePage
  GreenCityPage <|-- NotificationsPage
  GreenCityPage <|-- AllHabitsPage
  GreenCityPage <|-- HabitFormPage
  GreenCityPage <|-- CustomHabitPage
  GreenCityPage <|-- FriendsPage
  GreenCityPage <|-- FriendProfilePage
  GreenCityPage <|-- UnsubscribePage
```

`ConfirmRestorePasswordPage` наслідує `BasePage`, бо на `#/auth/restore` є хедер і немає футера GreenCity. Решта екранів `#/greenCity/**` наслідує `GreenCityPage`.

## Оболонка

```mermaid
classDiagram
  class GreenCityPage {
    +header HeaderComponent
    +footer FooterComponent
  }
  class HeaderComponent {
    +languageSwitcher LanguageSwitcher
    +openEcoNews() NewsListPage
    +openEvents() EventsListPage
    +openPlaces() PlacesPage
    +openAbout() AboutPage
    +openMyHabits() ProfilePage
    +openSignIn() AuthModal
    +openSignUp() AuthModal
    +openSearch() SearchPopup
    +openNotifications() NotificationsPopup
    +openSaved() NewsListPage
    +signOut() HomePage
    +toggleBurger() void
    +getUserName() String
  }
  class FooterComponent {
    +openEcoNews() NewsListPage
    +openEvents() EventsListPage
    +openPlaces() PlacesPage
    +openAbout() AboutPage
    +openMySpace() ProfilePage
    +clickLogo() HomePage
  }
  class LanguageSwitcher {
    +select(language) void
    +getCurrent() String
  }
  class AuthModal {
    +signIn(email, password) GreenCityPage
    +signUp(name, email, password) void
    +openRestore() void
    +sendRestoreEmail(email) void
    +signInWithGoogle() void
    +getError() String
    +close() void
  }
  class SearchPopup {
    +search(query) void
    +getResultCount() int
    +openResult(title) GreenCityPage
    +seeAll() SearchResultsPage
    +isEmpty() boolean
    +close() void
  }
  class NotificationsPopup {
    +getItems() String
    +openAll() NotificationsPage
    +close() void
  }
  GreenCityPage *-- HeaderComponent
  GreenCityPage *-- FooterComponent
  HeaderComponent *-- LanguageSwitcher
  HeaderComponent --> AuthModal
  HeaderComponent --> SearchPopup
  HeaderComponent --> NotificationsPopup
  BaseModal <|-- AuthModal
  BaseModal <|-- SearchPopup
  BaseModal <|-- NotificationsPopup
  BaseElement <|-- LanguageSwitcher
```

`openSaved()` веде на `NewsListPage` у режимі `isBookmark=true`. Пункт UBS у хедері й футері є на екрані, методу під нього немає.

`AuthModal` перемикає види `sign-in`, `sign-up` і `restore-password` без зміни URL. Підтвердження листа, розблокування і текст помилки — методи цієї ж модалки, не окремі класи. `signIn` повертає сторінку, з якої модалку відкрили.

## Головна, About, відписка

```mermaid
classDiagram
  class HomePage {
    +hero HomeHero
    +stats HomeStats
    +ecoNews HomeEcoNews
    +subscribe SubscribeForm
  }
  class HomeHero {
    +getTitle() String
    +startHabit() ProfilePage
  }
  class HomeStats {
    +getUsersAmount() String
    +getStat(name) String
  }
  class HomeEcoNews {
    +getCards() HomeNewsCard
    +openCard(title) NewsDetailPage
  }
  class HomeNewsCard {
    +getTitle() String
    +open() NewsDetailPage
  }
  class SubscribeForm {
    +subscribe(email) void
    +getMessage() String
  }
  class AboutPage {
    +getTitle() String
    +getVisionCards() VisionCard
  }
  class VisionCard {
    +getTitle() String
    +getText() String
  }
  class UnsubscribePage {
    +getMessage() String
  }
  HomePage *-- HomeHero
  HomePage *-- HomeStats
  HomePage *-- HomeEcoNews
  HomePage *-- SubscribeForm
  HomeEcoNews *-- HomeNewsCard
  HomeNewsCard --> NewsDetailPage
  AboutPage *-- VisionCard
```

Для гостя `startHabit()` відкриває `AuthModal`, а не профіль. Після входу той самий сценарій потрапляє на `ProfilePage`.

## Новини

```mermaid
classDiagram
  class NewsListPage {
    +filterBar NewsFilterBar
    +savedTabs SavedTabs
    +getCards() NewsCard
    +isSavedMode() boolean
    +isEmpty() boolean
    +loadMore() void
  }
  class NewsFilterBar {
    +tags TagFilter
    +search(text) void
    +clearSearch() void
    +toggleBookmark() void
    +showGallery() void
    +showList() void
    +openCreate() CreateEditNewsPage
  }
  class NewsCard {
    +getTitle() String
    +getAuthor() String
    +open() NewsDetailPage
    +toggleFavorite() void
    +isFavorite() boolean
  }
  class NewsDetailPage {
    +content NewsContent
    +related RelatedNews
    +comments CommentsContainer
    +isPreview() boolean
    +back() NewsListPage
    +edit() CreateEditNewsPage
    +delete() ConfirmDialog
    +publish() NewsDetailPage
  }
  class NewsContent {
    +getTitle() String
    +getTags() String
    +getAuthor() String
    +getText() String
    +like() void
    +isLiked() boolean
    +share(network) void
  }
  class RelatedNews {
    +open(title) NewsDetailPage
  }
  class CreateEditNewsPage {
    +form NewsEditorForm
    +preview() NewsDetailPage
    +publish() NewsDetailPage
    +leave() ConfirmDialog
  }
  class NewsEditorForm {
    +editor RichTextEditor
    +tags TagSelect
    +setTitle(title) NewsEditorForm
    +setSource(url) NewsEditorForm
    +uploadImage(path) NewsEditorForm
  }
  class SavedTabs {
    +openNews() NewsListPage
    +openEvents() EventsListPage
    +openPlaces() PlacesPage
  }
  NewsListPage *-- NewsFilterBar
  NewsListPage *-- NewsCard
  NewsListPage o-- SavedTabs
  NewsFilterBar *-- TagFilter
  NewsCard --> NewsDetailPage
  NewsDetailPage *-- NewsContent
  NewsDetailPage *-- RelatedNews
  NewsDetailPage *-- CommentsContainer
  NewsDetailPage --> CreateEditNewsPage
  NewsDetailPage --> ConfirmDialog
  CreateEditNewsPage *-- NewsEditorForm
  CreateEditNewsPage --> NewsDetailPage
  NewsEditorForm *-- RichTextEditor
  NewsEditorForm *-- TagSelect
```

`NewsDetailPage` обслуговує і `#/greenCity/news/:id`, і `#/greenCity/news/preview`. `publish()` існує лише в режимі прев'ю. `SavedTabs` з'являється на списку, коли `isSavedMode()` істинний. Для гостя `openCreate()` відкриває `AuthModal`.

## Події

```mermaid
classDiagram
  class EventsListPage {
    +filterBar EventFilterBar
    +savedTabs SavedTabs
    +getCards() EventCard
    +isSavedMode() boolean
    +isEmpty() boolean
    +loadMore() void
  }
  class EventFilterBar {
    +timeFilter MultiSelectFilter
    +locationFilter MultiSelectFilter
    +statusFilter MultiSelectFilter
    +typeFilter MultiSelectFilter
    +search(text) void
    +clearSearch() void
    +toggleBookmark() void
    +showGallery() void
    +showList() void
    +openCreate() EventEditorPage
    +openAddCity() AddCityModal
  }
  class EventCard {
    +getTitle() String
    +getDate() String
    +open() EventDetailsPage
    +toggleFavorite() void
  }
  class EventDetailsPage {
    +summary EventSummary
    +facts EventFacts
    +description EventDescription
    +comments CommentsContainer
    +isPreview() boolean
    +back() EventsListPage
    +edit() EventEditorPage
    +delete() ConfirmDialog
    +publish() EventDetailsPage
  }
  class EventSummary {
    +getTitle() String
    +getOrganizer() String
    +getTags() String
    +like() void
    +isLiked() boolean
  }
  class EventFacts {
    +getDate() String
    +getLocation() String
    +getLink() String
    +save() void
    +join() void
    +cancelJoin() void
    +openRate() RateEventModal
  }
  class EventDescription {
    +getText() String
  }
  class EventEditorPage {
    +information EventInformationForm
    +dates EventDatesForm
    +preview() EventDetailsPage
    +publish() EventDetailsPage
    +leave() ConfirmDialog
  }
  class EventInformationForm {
    +editor RichTextEditor
    +tags TagSelect
    +setTitle(title) EventInformationForm
    +uploadImage(path) EditEventImageModal
  }
  class EventDatesForm {
    +setOnline(link) EventDatesForm
    +setOffline(place) EventDatesForm
    +setDate(start, finish) EventDatesForm
  }
  class RateEventModal {
    +setRate(value) void
    +submit() EventDetailsPage
  }
  class AddCityModal {
    +setCity(name) void
    +submit() EventsListPage
  }
  EventsListPage *-- EventFilterBar
  EventsListPage *-- EventCard
  EventsListPage o-- SavedTabs
  EventFilterBar --> EventEditorPage
  EventFilterBar --> AddCityModal
  EventCard --> EventDetailsPage
  EventDetailsPage *-- EventSummary
  EventDetailsPage *-- EventFacts
  EventDetailsPage *-- EventDescription
  EventDetailsPage *-- CommentsContainer
  EventFacts --> RateEventModal
  EventDetailsPage --> EventEditorPage
  EventEditorPage *-- EventInformationForm
  EventEditorPage *-- EventDatesForm
  EventInformationForm --> EditEventImageModal
  EventEditorPage --> EventDetailsPage
```

`EventDetailsPage` також покриває `#/greenCity/events/preview`: у прев'ю доступні `publish()` і повернення в редактор, а join і коментарі приховані. Для гостя `openCreate()` і `join()` відкривають `AuthModal`.

## Мапа

```mermaid
classDiagram
  class PlacesPage {
    +filterBar PlaceFilterBar
    +list PlaceListPanel
    +details PlaceDetailsPanel
    +map MapCanvas
    +savedTabs SavedTabs
    +isSavedMode() boolean
  }
  class PlaceFilterBar {
    +nameSearch PlaceSearchField
    +location LocationAutocomplete
    +tags TagFilter
    +search(name) void
    +chooseLocation(text) void
    +openMoreOptions() void
    +openAddPlace() AddPlaceModal
  }
  class PlaceListPanel {
    +getNames() String
    +select(name) void
    +toggleFavorite(name) void
  }
  class PlaceDetailsPanel {
    +getName() String
    +getAddress() String
    +getRating() String
    +toggleFavorite() void
    +close() void
  }
  class MapCanvas {
    +selectMarker(name) void
  }
  class AddPlaceModal {
    +address LocationAutocomplete
    +tags TagSelect
    +setName(name) AddPlaceModal
    +setOpeningHours(from, to) AddPlaceModal
    +submit() PlacesPage
    +close() void
  }
  PlacesPage *-- PlaceFilterBar
  PlacesPage *-- PlaceListPanel
  PlacesPage *-- PlaceDetailsPanel
  PlacesPage *-- MapCanvas
  PlacesPage o-- SavedTabs
  PlaceFilterBar *-- PlaceSearchField
  PlaceFilterBar *-- LocationAutocomplete
  PlaceFilterBar *-- TagFilter
  PlaceFilterBar --> AddPlaceModal
  PlaceListPanel --> PlaceDetailsPanel
  MapCanvas --> PlaceDetailsPanel
  AddPlaceModal *-- LocationAutocomplete
  AddPlaceModal *-- TagSelect
```

Вибір у списку і клік по маркеру заповнюють один і той самий `PlaceDetailsPanel`. `AddPlaceModal` включає вибір часу роботи, з якого в клієнті починається цей потік.

## Профіль, звички, друзі

```mermaid
classDiagram
  class ProfilePage {
    +profileHeader ProfileHeader
    +dashboard ProfileDashboard
    +calendar ProfileCalendar
    +achievements AchievementsBlock
    +friends FriendsPreview
    +ecoPlaces EcoPlacesBlock
    +todo TodoList
  }
  class ProfileHeader {
    +avatar UserAvatar
    +getName() String
    +getProgress() String
    +openEdit() EditProfilePage
    +openSettings() UserSettingsModal
    +openNotifications() NotificationsPage
  }
  class ProfileDashboard {
    +openHabit(title) HabitFormPage
    +openAllHabits() AllHabitsPage
    +openNews(title) NewsDetailPage
    +openEvent(title) EventDetailsPage
  }
  class ProfileCalendar {
    +openDay(date) CalendarHabitsPopup
  }
  class AchievementsBlock {
    +getTitles() String
    +openAll() AchievementsModal
  }
  class FriendsPreview {
    +openAll() FriendsPage
  }
  class EcoPlacesBlock {
    +getPlaces() String
  }
  class TodoList {
    +add(text) void
    +toggle(text) void
    +getItems() String
  }
  class EditProfilePage {
    +setName(name) EditProfilePage
    +setCity(city) EditProfilePage
    +setSocial(network, url) EditProfilePage
    +changePhoto() EditPhotoModal
    +save() ProfilePage
    +leave() ConfirmDialog
  }
  class NotificationsPage {
    +getItems() String
    +markAsRead(text) void
  }
  class UserSettingsModal {
    +setShowEcoPlaces(visible) void
    +setShowTodo(visible) void
    +save() ProfilePage
  }
  class AchievementsModal {
    +getTitles() String
    +close() void
  }
  class CalendarHabitsPopup {
    +getHabits() String
    +close() void
  }
  class EditPhotoModal {
    +upload(path) void
    +save() EditProfilePage
  }
  ProfilePage *-- ProfileHeader
  ProfilePage *-- ProfileDashboard
  ProfilePage *-- ProfileCalendar
  ProfilePage *-- AchievementsBlock
  ProfilePage *-- FriendsPreview
  ProfilePage *-- EcoPlacesBlock
  ProfilePage *-- TodoList
  ProfileHeader *-- UserAvatar
  ProfileHeader --> EditProfilePage
  ProfileHeader --> UserSettingsModal
  ProfileHeader --> NotificationsPage
  ProfileDashboard --> HabitFormPage
  ProfileDashboard --> AllHabitsPage
  AchievementsBlock --> AchievementsModal
  ProfileCalendar --> CalendarHabitsPopup
  EditProfilePage --> EditPhotoModal
  EditProfilePage --> ConfirmDialog
```

`EcoPlacesBlock` і `TodoList` є на сторінці лише коли користувач увімкнув їх у `UserSettingsModal`.

```mermaid
classDiagram
  class AllHabitsPage {
    +filters HabitFilterBar
    +getCards() HabitCard
    +isEmpty() boolean
    +loadMore() void
    +backToProfile() ProfilePage
    +openCreate() CustomHabitPage
  }
  class HabitFilterBar {
    +filters MultiSelectFilter
    +reset() void
  }
  class HabitCard {
    +getTitle() String
    +open() HabitFormPage
  }
  class HabitFormPage {
    +setDuration(days) HabitFormPage
    +addTodo(text) HabitFormPage
    +inviteFriends() InviteFriendsModal
    +save() ProfilePage
    +back() AllHabitsPage
  }
  class CustomHabitPage {
    +tags TagSelect
    +setTitle(title) CustomHabitPage
    +setDescription(text) CustomHabitPage
    +save() AllHabitsPage
    +back() AllHabitsPage
  }
  class InviteFriendsModal {
    +select(name) void
    +confirm() HabitFormPage
  }
  AllHabitsPage *-- HabitFilterBar
  AllHabitsPage *-- HabitCard
  HabitFilterBar *-- MultiSelectFilter
  HabitCard --> HabitFormPage
  AllHabitsPage --> CustomHabitPage
  HabitFormPage --> InviteFriendsModal
  CustomHabitPage *-- TagSelect
```

`HabitFormPage` — це і взяття звички з галереї (`addhabit`), і редагування вже призначеної (`edithabit`). `CustomHabitPage` — створення і редагування власної звички.

```mermaid
classDiagram
  class FriendsPage {
    +tabs FriendTabs
    +getItems() FriendItem
    +backToProfile() ProfilePage
  }
  class FriendTabs {
    +search(name) void
    +openAll() FriendsPage
    +openRecommended() FriendsPage
    +openRequests() FriendsPage
  }
  class FriendItem {
    +getName() String
    +open() FriendProfilePage
    +add() void
    +accept() void
    +decline() void
  }
  class FriendProfilePage {
    +getName() String
    +addFriend() void
    +acceptRequest() void
    +declineRequest() void
    +removeFriend() ConfirmDialog
    +back() FriendsPage
  }
  class FriendsListPopup {
    +getNames() String
    +open(name) FriendProfilePage
    +close() void
  }
  FriendsPage *-- FriendTabs
  FriendsPage *-- FriendItem
  FriendItem --> FriendProfilePage
  FriendProfilePage --> ConfirmDialog
  ProfilePage --> FriendsListPopup
```

`FriendsPage` одна на три вкладки. `FriendTabs.openAll`, `openRecommended` і `openRequests` лишаються на цьому класі й змінюють вкладку. `FriendsListPopup` відкривається з профілю і не переходить на маршрут друзів.

## Пошук і відновлення пароля

```mermaid
classDiagram
  class SearchPopup {
    +search(query) void
    +getResultCount() int
    +results SearchResultItem
    +seeAll() SearchResultsPage
    +isEmpty() boolean
    +close() void
  }
  class SearchResultsPage {
    +search(query) void
    +getItems(category) SearchResultItem
    +isEmpty() boolean
  }
  class SearchResultItem {
    +getTitle() String
    +getCategory() String
    +open() GreenCityPage
  }
  class ConfirmRestorePasswordPage {
    +setPassword(password, confirm) void
    +submit() HomePage
    +getError() String
  }
  SearchPopup *-- SearchResultItem
  SearchResultsPage *-- SearchResultItem
  SearchPopup --> SearchResultsPage
  SearchResultItem --> NewsDetailPage
  SearchResultItem --> EventDetailsPage
  SearchResultItem --> PlacesPage
```

`open()` у результаті повертає сторінку категорії: новину, подію або мапу. `ConfirmRestorePasswordPage` не входить у `GreenCityPage`.

## Спільні віджети

```mermaid
classDiagram
  class CommentsContainer {
    +add(text) CommentItem
    +getComments() CommentItem
    +nextPage() void
  }
  class CommentItem {
    +getText() String
    +getAuthor() String
    +like() void
    +reply(text) CommentItem
    +edit(text) void
    +delete() ConfirmDialog
  }
  class ConfirmDialog {
    +getTitle() String
    +getMessage() String
    +confirm() void
    +cancel() void
  }
  class TagFilter {
    +select(tag) void
    +clear() void
    +getSelected() String
  }
  class TagSelect {
    +select(tag) void
    +getSelected() String
  }
  class NoDataBlock {
    +getTitle() String
    +getText() String
  }
  class RichTextEditor {
    +setText(text) void
    +getText() String
  }
  class MultiSelectFilter {
    +select(option) void
    +clear() void
    +getSelected() String
  }
  CommentsContainer *-- CommentItem
  CommentItem --> ConfirmDialog
  BaseComponent <|-- CommentsContainer
  BaseComponent <|-- CommentItem
  BaseComponent <|-- TagFilter
  BaseComponent <|-- NoDataBlock
  BaseModal <|-- ConfirmDialog
  BaseElement <|-- TagSelect
  BaseElement <|-- RichTextEditor
  BaseElement <|-- MultiSelectFilter
```

`CommentsContainer` стоїть на `NewsDetailPage` і на `EventDetailsPage`. `ConfirmDialog` один на видалення новини, події, коментаря, друга і на вихід із форми з незбереженими змінами. `NoDataBlock` показують списки новин, подій і звичок, коли видача порожня.

`TagFilter` — фільтр на сторінці списку. `TagSelect` — вибір тегів у формі новини, події, місця і власної звички.
