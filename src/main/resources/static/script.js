const baseUrl = "http://localhost:8080";

document.getElementById('fetchAllWords').addEventListener('click', fetchAllWords);
document.getElementById('addWord').addEventListener('click', addWord);
document.getElementById('addCategory').addEventListener('click', addCategory);

document.getElementById('randomTest').addEventListener('click', generateRandomTest);
document.getElementById('testLastDay').addEventListener('click', generateLastDayTest);
document.getElementById('testLastWeek').addEventListener('click', generateLastWeekTest);
document.getElementById('testLastMonth').addEventListener('click', generateLastMonthTest);

function generateRandomTest() {
    const count = prompt("Enter the number of random words:");
    fetch(`${baseUrl}/tests/random?count=${count}`)
        .then(response => response.json())
        .then(data => generateTest(data))
        .catch(error => console.error('Error generating random test:', error));
}


function generateTest(words) {
    const testForm = document.getElementById('testResult');
    testForm.innerHTML = '';
    const wordMap = new Map();

    words.forEach(word => {
        wordMap.set(word.originalWord, word.translatedWord);

        const div = document.createElement('div');
        div.innerHTML = `
            <label>${word.originalWord}</label>
            <input type="text" class="userAnswer" placeholder="Your translation" data-original="${word.originalWord}">
        `;
        testForm.appendChild(div);
    });

    // Przycisk do sprawdzania odpowiedzi
    const submitBtn = document.createElement('button');
    submitBtn.textContent = 'Submit Answers';
    submitBtn.addEventListener('click', () => checkTestAnswers(wordMap));
    testForm.appendChild(submitBtn);
}


function checkTestAnswers(wordMap) {
    const userAnswers = document.querySelectorAll('.userAnswer');
    userAnswers.forEach(answerInput => {
        const originalWord = answerInput.dataset.original;
        const correctTranslation = wordMap.get(originalWord).toLowerCase();
        const userTranslation = answerInput.value.trim().toLowerCase();

        if (userTranslation === correctTranslation) {
            answerInput.style.borderColor = 'green';
        } else {
            answerInput.style.borderColor = 'red';
        }
    });
}

function generateLastDayTest() {
    fetch(`${baseUrl}/tests/from-last-day`)
        .then(response => response.json())
        .then(data => generateTest(data))
        .catch(error => console.error('Error generating last day test:', error));
}

function generateLastWeekTest() {
    fetch(`${baseUrl}/tests/from-last-week`)
        .then(response => response.json())
        .then(data => generateTest(data))
        .catch(error => console.error('Error generating last week test:', error));
}

function generateLastMonthTest() {
    fetch(`${baseUrl}/tests/from-last-month`)
        .then(response => response.json())
        .then(data => generateTest(data))
        .catch(error => console.error('Error generating last month test:', error));
}

function addCategory() {
    const categoryName = document.getElementById('newCategoryName').value;

    if (!categoryName) {
        alert('Please enter a category name');
        return;
    }

    const categoryData = { name: categoryName };

    fetch(`${baseUrl}/categories`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(categoryData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to add category');
        }
        return response.json();
    })
    .then(() => {
        alert('Category added successfully!');
        loadCategories();
        document.getElementById('newCategoryName').value = '';
    })
    .catch(error => console.error('Error adding category:', error));
}



function loadCategories() {
    fetch(`${baseUrl}/categories`)
        .then(response => {
            console.log('Response status:', response.status);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(categories => {
            console.log('Categories fetched from server:', categories);
            const categorySelect = document.getElementById('categorySelect');
            const categoriesList = document.getElementById('categoriesList');
            categorySelect.innerHTML = '<option value="">Select Category</option>';
            categoriesList.innerHTML = '';

            categories.forEach(category => {

                const option = document.createElement('option');
                option.value = category.id;
                option.textContent = category.name;
                categorySelect.appendChild(option);

                const li = document.createElement('li');
                li.innerHTML = `${category.name}
                    <button onclick="editCategory(${category.id}, '${category.name}')">Edit</button>
                    <button onclick="deleteCategory(${category.id})">Delete</button>
                    <button onclick="fetchCategoryWords(${category.id})">Show Words</button>`;
                categoriesList.appendChild(li);
            });
        })
        .catch(error => console.error('Error loading categories:', error));
}





function fetchCategoryWords(categoryId) {
    fetch(`${baseUrl}/words/category/${categoryId}`)
        .then(response => response.json())
        .then(words => {
            const wordsList = document.getElementById('wordsList');
            wordsList.innerHTML = '';

            words.forEach(word => {
                const li = document.createElement('li');
                li.innerHTML = `
                    ${word.originalWord} - ${word.translatedWord}
                    <button onclick="editWord(${word.id})">Edit</button>
                    <button onclick="deleteWord(${word.id})">Delete</button>
                `;
                wordsList.appendChild(li);
            });
        })
        .catch(error => console.error('Error fetching words for category:', error));
}


function editCategory(categoryId) {
    const newCategoryName = prompt('Enter new category name:');
    if (!newCategoryName) return;

    const categoryData = { name: newCategoryName };

    fetch(`${baseUrl}/categories/${categoryId}`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(categoryData)
    })
    .then(() => {
        alert('Category updated successfully!');
        loadCategories();
    })
    .catch(error => console.error('Error updating category:', error));
}


function deleteCategory(categoryId) {
    if (!confirm('Are you sure you want to delete this category?')) return;

    fetch(`${baseUrl}/categories/${categoryId}`, {
        method: 'DELETE'
    })
    .then(() => {
        alert('Category deleted successfully!');
        loadCategories();  // Odśwież listę kategorii
    })
    .catch(error => console.error('Error deleting category:', error));
}


function addWord() {
    const originalWord = document.getElementById('originalWord').value;
    const translatedWord = document.getElementById('translatedWord').value;
    const categoryId = document.getElementById('categorySelect').value;

    if (!originalWord || !translatedWord || !categoryId) {
        alert('Please fill out all fields and select a category');
        return;
    }

    const wordData = { originalWord, translatedWord };


    fetch(`${baseUrl}/words?categoryId=${categoryId}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(wordData)
    })
    .then(response => response.json())
    .then(() => {
        alert('Word added successfully!')
        document.getElementById('originalWord').value = '';
        document.getElementById('translatedWord').value = '';
        document.getElementById('categorySelect').value = '';
    })
    .catch(error => console.error('Error adding word:', error));
}



function editWord(wordId) {
    const newOriginalWord = prompt('Enter new original word:');
    const newTranslatedWord = prompt('Enter new translated word:');

    const wordData = { originalWord: newOriginalWord, translatedWord: newTranslatedWord };

    fetch(`${baseUrl}/words/${wordId}`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(wordData)
    })
    .then(() => {
        alert('Word updated successfully!');
        fetchAllWords();
    })
    .catch(error => console.error('Error updating word:', error));
}


function deleteWord(wordId) {
    fetch(`${baseUrl}/words/${wordId}`, {
        method: 'DELETE'
    })
    .then(() => {
        alert('Word deleted successfully!');
        fetchAllWords();
    })
    .catch(error => console.error('Error deleting word:', error));
}


function fetchAllWords() {
    fetch(`${baseUrl}/words`)
        .then(response => response.json())
        .then(data => {
            const wordsList = document.getElementById('wordsList');
            wordsList.innerHTML = '';
            data.forEach(word => {
                const li = document.createElement('li');
                li.innerHTML = `
                    ${word.originalWord} - ${word.translatedWord}
                    <button onclick="editWord(${word.id})">Edit</button>
                    <button onclick="deleteWord(${word.id})">Delete</button>
                `;
                wordsList.appendChild(li);
            });
        })
        .catch(error => console.error('Error fetching words:', error));
}


loadCategories();
