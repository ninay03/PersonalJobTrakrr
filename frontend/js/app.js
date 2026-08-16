const API_URL = "http://localhost:8080/application";

let applications = [];


async function loadApplications() {

    try {

        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Failed to fetch applications");
        }

        applications = await response.json();

        updateStatistics(applications);
        displayApplications(applications);
        await loadInterviews();
    } catch (error) {

        console.error("Error loading applications:", error);

    }
}


function updateStatistics(applications) {

    const total = applications.length;

    const applied = applications.filter(
        application => application.status === "APPLIED"
    ).length;

    const interviews = applications.filter(
        application => application.status === "INTERVIEW"
    ).length;

    const offers = applications.filter(
        application => application.status === "OFFER"
    ).length;


    document.getElementById("totalApplications").textContent = total;
    document.getElementById("appliedApplications").textContent = applied;
    document.getElementById("interviewApplications").textContent = interviews;
    document.getElementById("offerApplications").textContent = offers;
}


function displayApplications(applications) {

    const tableBody = document.getElementById("applicationsTableBody");

    tableBody.innerHTML = "";

    applications.forEach(application => {

        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${application.company}</td>
            <td>${application.role}</td>
            <td>${application.status}</td>
            <td>${application.deadline || "-"}</td>
            <td>
                <button
                    class="secondary-btn view-application-btn"
                    data-id="${application.id}">
                    View
                </button>
            </td>
        `;

        tableBody.appendChild(row);
    });


    const viewButtons = document.querySelectorAll(".view-application-btn");

    viewButtons.forEach(button => {

        button.addEventListener("click", function () {

            const applicationId = this.getAttribute("data-id");

            viewApplication(applicationId);

        });

    });
}


function filterApplications() {

    const searchTerm =
        document.getElementById("searchInput").value.toLowerCase();

    const selectedStatus =
        document.getElementById("statusFilter").value;


    const filteredApplications = applications.filter(application => {

        const matchesSearch =
            application.company.toLowerCase().includes(searchTerm) ||
            application.role.toLowerCase().includes(searchTerm);

        const matchesStatus =
            selectedStatus === "" ||
            application.status === selectedStatus;

        return matchesSearch && matchesStatus;

    });


    displayApplications(filteredApplications);
}


document
    .getElementById("searchInput")
    .addEventListener("input", filterApplications);


document
    .getElementById("statusFilter")
    .addEventListener("change", filterApplications);


async function viewApplication(id) {

    try {

        const response = await fetch(`${API_URL}/${id}`);

        if (!response.ok) {
            throw new Error("Failed to fetch application");
        }

        const application = await response.json();

        document.getElementById("editApplicationId").value = application.id;

        document.getElementById("editCompany").value =
            application.company || "";

        document.getElementById("editRole").value =
            application.role || "";

        document.getElementById("editJobUrl").value =
            application.jobUrl || "";

        document.getElementById("editAppliedDate").value =
            application.appliedDate || "";

        document.getElementById("editDeadline").value =
            application.deadline || "";

        document.getElementById("editResumeUrl").value =
            application.resumeUrl || "";

        document.getElementById("editNotes").value =
            application.notes || "";

        document.getElementById("viewCompany").textContent =
            application.company || "-";

        document.getElementById("viewRole").textContent =
            application.role || "-";

        document.getElementById("viewStatus").textContent =
            application.status || "-";

        document.getElementById("viewAppliedDate").textContent =
            application.appliedDate || "-";

        document.getElementById("viewJobUrl").textContent =
            application.jobUrl || "-";

        document.getElementById("viewDeadline").textContent =
            application.deadline || "-";

        document.getElementById("viewResumeUrl").textContent =
            application.resumeUrl || "-";

        document.getElementById("viewNotes").textContent =
            application.notes || "-";

        document.getElementById("viewCreatedAt").textContent =
            application.createdAt || "-";

        document.getElementById("viewUpdatedAt").textContent =
            application.updatedAt || "-";


        document
            .getElementById("viewApplicationModal")
            .classList.remove("hidden");

    } catch (error) {

        console.error("Error loading application:", error);

    }
}


loadApplications();


const applicationForm =
    document.getElementById("applicationForm");


applicationForm.addEventListener("submit", async function (event) {

    event.preventDefault();


    const applicationData = {

        company:
            document.getElementById("company").value,

        role:
            document.getElementById("role").value,

        jobUrl:
            document.getElementById("jobUrl").value,

        status:
            document.getElementById("status").value,

        deadline:
            document.getElementById("deadline").value,

        resumeUrl:
            document.getElementById("resumeUrl").value,

        notes:
            document.getElementById("notes").value

    };


    try {

        const response = await fetch(API_URL, {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(applicationData)

        });


        if (!response.ok) {

            throw new Error(
                "Failed to create application"
            );

        }


        const createdApplication =
            await response.json();


        console.log(
            "Application created:",
            createdApplication
        );


        applicationForm.reset();


        applicationModal.classList.add("hidden");


        await loadApplications();


    } catch (error) {

        console.error(
            "Error creating application:",
            error
        );

    }

});


// =========================
// Add Application Modal
// =========================

const addApplicationBtn =
    document.getElementById("addApplicationBtn");

const closeModalBtn =
    document.getElementById("closeModalBtn");

const cancelApplicationBtn =
    document.getElementById("cancelApplicationBtn");

const applicationModal =
    document.getElementById("applicationModal");


addApplicationBtn.addEventListener("click", function () {

    applicationModal.classList.remove("hidden");

});


closeModalBtn.addEventListener("click", function () {

    applicationModal.classList.add("hidden");

});


cancelApplicationBtn.addEventListener("click", function () {

    applicationModal.classList.add("hidden");

});


// =========================
// View Application Modal
// =========================

const viewApplicationModal =
    document.getElementById("viewApplicationModal");

const closeViewModalBtn =
    document.getElementById("closeViewModalBtn");

const closeViewApplicationBtn =
    document.getElementById("closeViewApplicationBtn");


closeViewModalBtn.addEventListener("click", function () {

    viewApplicationModal.classList.add("hidden");

});


closeViewApplicationBtn.addEventListener("click", function () {

    viewApplicationModal.classList.add("hidden");

});
const editApplicationBtn =
    document.getElementById("editApplicationBtn");

const editApplicationModal =
    document.getElementById("editApplicationModal");


editApplicationBtn.addEventListener("click", function () {

    viewApplicationModal.classList.add("hidden");

    editApplicationModal.classList.remove("hidden");

});
const editApplicationForm =
    document.getElementById("editApplicationForm");


editApplicationForm.addEventListener("submit", async function (event) {

    event.preventDefault();

    const id =
        document.getElementById("editApplicationId").value;


    const updatedApplication = {

        company:
            document.getElementById("editCompany").value,

        role:
            document.getElementById("editRole").value,

        jobUrl:
            document.getElementById("editJobUrl").value,

        appliedDate:
            document.getElementById("editAppliedDate").value,

        deadline:
            document.getElementById("editDeadline").value,

        resumeUrl:
            document.getElementById("editResumeUrl").value,

        notes:
            document.getElementById("editNotes").value

    };


    try {

        const response = await fetch(`${API_URL}/${id}`, {

            method: "PUT",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(updatedApplication)

        });


        if (!response.ok) {

            throw new Error(
                "Failed to update application"
            );

        }


        const updated =
            await response.json();


        console.log(
            "Application updated:",
            updated
        );


        editApplicationModal.classList.add("hidden");


        await loadApplications();


    } catch (error) {

        console.error(
            "Error updating application:",
            error
        );

    }

});
// =========================
// Delete Application
// =========================

const deleteApplicationBtn =
    document.getElementById("deleteApplicationBtn");


deleteApplicationBtn.addEventListener("click", async function () {

    const id =
        document.getElementById("editApplicationId").value;


    const confirmed = confirm(
        "Are you sure you want to delete this application?"
    );


    if (!confirmed) {
        return;
    }


    try {

        const response = await fetch(`${API_URL}/${id}`, {

            method: "DELETE"

        });


        if (!response.ok) {

            throw new Error(
                "Failed to delete application"
            );

        }


        console.log(
            "Application deleted successfully"
        );


        viewApplicationModal.classList.add("hidden");


        await loadApplications();


    } catch (error) {

        console.error(
            "Error deleting application:",
            error
        );

    }

});
// =========================
// Update Application Status
// =========================

const updateStatusBtn =
    document.getElementById("updateStatusBtn");


updateStatusBtn.addEventListener("click", async function () {

    const id =
        document.getElementById("editApplicationId").value;

    const newStatus =
        document.getElementById("newStatus").value;


    if (!newStatus) {

        alert("Please select a status.");

        return;
    }


    try {

        const response = await fetch(`${API_URL}/${id}`, {

            method: "PATCH",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(newStatus)

        });


        if (!response.ok) {

            const errorMessage =
                await response.text();

            throw new Error(
                errorMessage || "Failed to update application status"
            );

        }


        const updatedApplication =
            await response.json();


        console.log(
            "Status updated:",
            updatedApplication
        );


        document
            .getElementById("newStatus")
            .value = "";


        viewApplicationModal.classList.add("hidden");


        await loadApplications();


    } catch (error) {

        console.error(
            "Error updating status:",
            error
        );

        alert(error.message);

    }

});
// =========================
// Interview API
// =========================

const INTERVIEW_API_URL =
    "http://localhost:8080/interview-round";


// =========================
// Load Interviews
// =========================

async function loadInterviews() {

    const tableBody =
        document.getElementById("interviewsTableBody");

    tableBody.innerHTML = "";

    try {

        for (const application of applications) {

            const response = await fetch(
                `${API_URL}/${application.id}/interview-round`
            );

            if (!response.ok) {

                throw new Error(
                    "Failed to fetch interviews"
                );

            }

            const interviews =
                await response.json();


            interviews.forEach(interview => {

                displayInterview(
                    interview,
                    application
                );

            });

        }

    } catch (error) {

        console.error(
            "Error loading interviews:",
            error
        );

    }
}


// =========================
// Display Interview
// =========================

function displayInterview(
    interview,
    application
) {

    const tableBody =
        document.getElementById("interviewsTableBody");


    const row =
        document.createElement("tr");


    row.innerHTML = `

        <td>
            ${application.company}
        </td>

        <td>
            ${application.role}
        </td>

        <td>
            ${interview.roundName}
        </td>

        <td>
            ${interview.date || "-"}
        </td>

        <td>
            ${interview.outcome || "-"}
        </td>

        <td>

            <button
                    class="secondary-btn view-interview-btn"
                    data-id="${interview.id}">
                View
            </button>

        </td>

    `;


    tableBody.appendChild(row);


    const viewButton =
        row.querySelector(
            ".view-interview-btn"
        );


    viewButton.addEventListener(
        "click",
        function () {

            viewInterview(
                interview.id
            );

        }
    );

}


// =========================
// Populate Application Dropdown
// =========================

function populateInterviewApplications() {

    const select =
        document.getElementById(
            "interviewApplication"
        );


    select.innerHTML = `

        <option value="">
            Select Application
        </option>

    `;


    applications.forEach(application => {

        const option =
            document.createElement("option");


        option.value =
            application.id;


        option.textContent =
            `${application.company} - ${application.role}`;


        select.appendChild(option);

    });

}


// =========================
// Add Interview
// =========================

const interviewForm =
    document.getElementById(
        "interviewForm"
    );


interviewForm.addEventListener(
    "submit",
    async function (event) {

        event.preventDefault();


        const applicationId =
            document.getElementById(
                "interviewApplication"
            ).value;


        const interviewData = {

            roundName:
                document.getElementById(
                    "roundName"
                ).value,

            date:
                document.getElementById(
                    "interviewDate"
                ).value,

            outcome:
                document.getElementById(
                    "interviewOutcome"
                ).value,

            notes:
                document.getElementById(
                    "interviewNotes"
                ).value

        };


        try {

            const response =
                await fetch(
                    `${API_URL}/${applicationId}/interview-round`,
                    {

                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body:
                            JSON.stringify(
                                interviewData
                            )

                    }
                );


            if (!response.ok) {

                const errorMessage =
                    await response.text();

                throw new Error(
                    errorMessage ||
                    "Failed to create interview"
                );

            }


            const createdInterview =
                await response.json();


            console.log(
                "Interview created:",
                createdInterview
            );


            interviewForm.reset();


            document
                .getElementById(
                    "interviewModal"
                )
                .classList.add("hidden");


            await loadInterviews();


        } catch (error) {

            console.error(
                "Error creating interview:",
                error
            );

            alert(error.message);

        }

    }
);


// =========================
// Add Interview Modal
// =========================

const addInterviewBtn =
    document.getElementById(
        "addInterviewBtn"
    );


const interviewModal =
    document.getElementById(
        "interviewModal"
    );


const closeInterviewModalBtn =
    document.getElementById(
        "closeInterviewModalBtn"
    );


const cancelInterviewBtn =
    document.getElementById(
        "cancelInterviewBtn"
    );


addInterviewBtn.addEventListener(
    "click",
    function () {

        populateInterviewApplications();

        interviewModal
            .classList
            .remove("hidden");

    }
);


closeInterviewModalBtn.addEventListener(
    "click",
    function () {

        interviewModal
            .classList
            .add("hidden");

    }
);


cancelInterviewBtn.addEventListener(
    "click",
    function () {

        interviewModal
            .classList
            .add("hidden");

    }
);


// =========================
// View Interview
// =========================

async function viewInterview(id) {

    try {

        const response =
            await fetch(
                `${INTERVIEW_API_URL}/${id}`
            );


        if (!response.ok) {

            throw new Error(
                "Failed to fetch interview"
            );

        }


        const interview =
            await response.json();


        const application =
            applications.find(
                app =>
                    app.id ===
                    interview.jobApplicationId
            );


        document.getElementById(
            "viewInterviewCompany"
        ).textContent =
            application
                ? application.company
                : "-";


        document.getElementById(
            "viewInterviewRole"
        ).textContent =
            application
                ? application.role
                : "-";


        document.getElementById(
            "viewInterviewRoundName"
        ).textContent =
            interview.roundName || "-";


        document.getElementById(
            "viewInterviewDate"
        ).textContent =
            interview.date || "-";


        document.getElementById(
            "viewInterviewOutcome"
        ).textContent =
            interview.outcome || "-";


        document.getElementById(
            "viewInterviewNotes"
        ).textContent =
            interview.notes || "-";


        document.getElementById(
            "editInterviewId"
        ).value =
            interview.id;


        document.getElementById(
            "editRoundName"
        ).value =
            interview.roundName || "";


        document.getElementById(
            "editInterviewDate"
        ).value =
            interview.date || "";


        document.getElementById(
            "editInterviewOutcome"
        ).value =
            interview.outcome || "";


        document.getElementById(
            "editInterviewNotes"
        ).value =
            interview.notes || "";


        document.getElementById(
            "editInterviewApplication"
        ).textContent =
            application
                ? `${application.company} - ${application.role}`
                : "-";


        document
            .getElementById(
                "viewInterviewModal"
            )
            .classList
            .remove("hidden");


    } catch (error) {

        console.error(
            "Error loading interview:",
            error
        );

        alert(error.message);

    }

}


// =========================
// View Interview Modal
// =========================

const viewInterviewModal =
    document.getElementById(
        "viewInterviewModal"
    );


const closeViewInterviewModalBtn =
    document.getElementById(
        "closeViewInterviewModalBtn"
    );


const closeViewInterviewBtn =
    document.getElementById(
        "closeViewInterviewBtn"
    );


closeViewInterviewModalBtn.addEventListener(
    "click",
    function () {

        viewInterviewModal
            .classList
            .add("hidden");

    }
);


closeViewInterviewBtn.addEventListener(
    "click",
    function () {

        viewInterviewModal
            .classList
            .add("hidden");

    }
);


// =========================
// Edit Interview
// =========================

const editInterviewBtn =
    document.getElementById(
        "editInterviewBtn"
    );


const editInterviewModal =
    document.getElementById(
        "editInterviewModal"
    );


editInterviewBtn.addEventListener(
    "click",
    function () {

        viewInterviewModal
            .classList
            .add("hidden");


        editInterviewModal
            .classList
            .remove("hidden");

    }
);


// =========================
// Update Interview
// =========================

const editInterviewForm =
    document.getElementById(
        "editInterviewForm"
    );


editInterviewForm.addEventListener(
    "submit",
    async function (event) {

        event.preventDefault();


        const id =
            document.getElementById(
                "editInterviewId"
            ).value;


        const updatedInterview = {

            roundName:
                document.getElementById(
                    "editRoundName"
                ).value,

            date:
                document.getElementById(
                    "editInterviewDate"
                ).value,

            outcome:
                document.getElementById(
                    "editInterviewOutcome"
                ).value,

            notes:
                document.getElementById(
                    "editInterviewNotes"
                ).value

        };


        try {

            const response =
                await fetch(
                    `${INTERVIEW_API_URL}/${id}`,
                    {

                        method: "PUT",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body:
                            JSON.stringify(
                                updatedInterview
                            )

                    }
                );


            if (!response.ok) {

                const errorMessage =
                    await response.text();

                throw new Error(
                    errorMessage ||
                    "Failed to update interview"
                );

            }


            const updated =
                await response.json();


            console.log(
                "Interview updated:",
                updated
            );


            editInterviewModal
                .classList
                .add("hidden");


            await loadInterviews();


        } catch (error) {

            console.error(
                "Error updating interview:",
                error
            );

            alert(error.message);

        }

    }
);


// =========================
// Edit Interview Modal Controls
// =========================

const closeEditInterviewModalBtn =
    document.getElementById(
        "closeEditInterviewModalBtn"
    );


const cancelEditInterviewBtn =
    document.getElementById(
        "cancelEditInterviewBtn"
    );


closeEditInterviewModalBtn.addEventListener(
    "click",
    function () {

        editInterviewModal
            .classList
            .add("hidden");

    }
);


cancelEditInterviewBtn.addEventListener(
    "click",
    function () {

        editInterviewModal
            .classList
            .add("hidden");

    }
);


// =========================
// Delete Interview
// =========================

const deleteInterviewBtn =
    document.getElementById(
        "deleteInterviewBtn"
    );


deleteInterviewBtn.addEventListener(
    "click",
    async function () {

        const id =
            document.getElementById(
                "editInterviewId"
            ).value;


        const confirmed =
            confirm(
                "Are you sure you want to delete this interview?"
            );


        if (!confirmed) {
            return;
        }


        try {

            const response =
                await fetch(
                    `${INTERVIEW_API_URL}/${id}`,
                    {
                        method: "DELETE"
                    }
                );


            if (!response.ok) {

                const errorMessage =
                    await response.text();

                throw new Error(
                    errorMessage ||
                    "Failed to delete interview"
                );

            }


            console.log(
                "Interview deleted successfully"
            );


            viewInterviewModal
                .classList
                .add("hidden");


            await loadInterviews();


        } catch (error) {

            console.error(
                "Error deleting interview:",
                error
            );

            alert(error.message);

        }

    }
);