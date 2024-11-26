// Simulated Backend Data
let courses = [
    { title: "Introduction to JavaScript", description: "Learn the basics of JavaScript." },
    { title: "Web Development", description: "Build websites using HTML, CSS, and JS." },
];

// Display Courses
function displayCourses() {
    const courseList = document.getElementById("course-list");
    courseList.innerHTML = ""; // Clear the list
    courses.forEach((course, index) => {
        const li = document.createElement("li");
        li.innerHTML = `<strong>${course.title}</strong>: ${course.description}
            <button onclick="deleteCourse(${index})">Delete</button>`;
        courseList.appendChild(li);
    });
}

// Add a New Course
function addCourse() {
    const title = document.getElementById("course-title").value;
    const description = document.getElementById("course-description").value;

    if (title && description) {
        courses.push({ title, description });
        document.getElementById("course-title").value = "";
        document.getElementById("course-description").value = "";
        displayCourses(); // Refresh the course list
    } else {
        alert("Please fill in both fields.");
    }
}

// Delete a Course
function deleteCourse(index) {
    courses.splice(index, 1); // Remove the course at the specified index
    displayCourses(); // Refresh the course list
}

// Initialize
document.addEventListener("DOMContentLoaded", displayCourses);
