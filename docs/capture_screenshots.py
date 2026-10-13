"""
CampusConnect - Automated Screenshot Capture Script
Uses Playwright to capture all key UI screens for the Internship Report
"""
import os
from playwright.sync_api import sync_playwright

BASE_URL = "http://localhost:4200"
SCREENSHOT_DIR = os.path.join(os.path.dirname(__file__), "screenshots")
os.makedirs(SCREENSHOT_DIR, exist_ok=True)

ADMIN_EMAIL = "projectadmin@sms.com"
ADMIN_PASS = "Admin@123"
STUDENT_EMAIL = "student@sms.com"
STUDENT_PASS = "Student@123"
FACULTY_EMAIL = "faculty@sms.com"
FACULTY_PASS = "Faculty@123"

def save(page, name):
    path = os.path.join(SCREENSHOT_DIR, f"{name}.png")
    page.screenshot(path=path, full_page=True)
    print(f"  [OK] {name}.png")

def login(page, email, password):
    page.goto(f"{BASE_URL}/login", wait_until="networkidle")
    page.wait_for_selector("input[type='email'], input[name='email'], input[formcontrolname='email']", timeout=10000)
    page.fill("input[type='email'], input[name='email'], input[formcontrolname='email']", email)
    page.fill("input[type='password'], input[name='password'], input[formcontrolname='password']", password)
    page.click("button[type='submit']")
    page.wait_for_url(lambda url: "/login" not in url, timeout=10000)
    page.wait_for_load_state("networkidle")

def logout(page):
    # Try navbar logout button
    try:
        page.click("button:has-text('Logout'), a:has-text('Logout'), [id*='logout']", timeout=3000)
        page.wait_for_url(lambda url: "/login" in url, timeout=5000)
    except:
        page.goto(f"{BASE_URL}/login")
        page.evaluate("localStorage.clear()")
        page.goto(f"{BASE_URL}/login")

with sync_playwright() as p:
    browser = p.chromium.launch(headless=True)
    context = browser.new_context(viewport={"width": 1440, "height": 900})
    page = context.new_page()

    print("\n=== WEEK 1: Project Setup & Core Backend - Login Screen ===")
    page.goto(f"{BASE_URL}/login", wait_until="networkidle")
    page.wait_for_timeout(1500)
    save(page, "01_login_page")

    # Fill login form (partially filled state for screenshot)
    try:
        page.fill("input[type='email'], input[formcontrolname='email']", ADMIN_EMAIL)
        save(page, "01b_login_form_filled")
    except:
        pass

    print("\n=== WEEK 2: Backend APIs & CRUD - Admin Screens ===")
    login(page, ADMIN_EMAIL, ADMIN_PASS)
    page.wait_for_timeout(2000)

    # Dashboard
    page.goto(f"{BASE_URL}/admin/dashboard", wait_until="networkidle")
    page.wait_for_timeout(2000)
    save(page, "02_admin_dashboard")

    # Department CRUD
    try:
        page.goto(f"{BASE_URL}/admin/departments", wait_until="networkidle")
        page.wait_for_timeout(2000)
        save(page, "03_department_management")
    except:
        print("  [SKIP] departments page")

    # Faculty Management
    try:
        page.goto(f"{BASE_URL}/admin/faculty", wait_until="networkidle")
        page.wait_for_timeout(2000)
        save(page, "04_faculty_management")
    except:
        print("  [SKIP] faculty page")

    # Student Management
    try:
        page.goto(f"{BASE_URL}/admin/students", wait_until="networkidle")
        page.wait_for_timeout(2000)
        save(page, "05_student_management")
    except:
        print("  [SKIP] students page")

    # Course Management
    try:
        page.goto(f"{BASE_URL}/admin/courses", wait_until="networkidle")
        page.wait_for_timeout(2000)
        save(page, "06_course_management")
    except:
        print("  [SKIP] courses page")

    print("\n=== WEEK 3: Security & JWT - Admin Auth & RBAC Screens ===")

    # Student-Course Enrollment
    try:
        page.goto(f"{BASE_URL}/admin/student-courses", wait_until="networkidle")
        page.wait_for_timeout(2000)
        save(page, "07_student_course_enrollment")
    except:
        print("  [SKIP] student-courses page")

    # Attendance Management
    try:
        page.goto(f"{BASE_URL}/admin/attendance", wait_until="networkidle")
        page.wait_for_timeout(2000)
        save(page, "08_attendance_management")
    except:
        print("  [SKIP] attendance page")

    # Marks Management
    try:
        page.goto(f"{BASE_URL}/admin/marks", wait_until="networkidle")
        page.wait_for_timeout(2000)
        save(page, "09_marks_management")
    except:
        print("  [SKIP] marks page")

    # Announcements
    try:
        page.goto(f"{BASE_URL}/admin/announcements", wait_until="networkidle")
        page.wait_for_timeout(2000)
        save(page, "10_announcements")
    except:
        print("  [SKIP] announcements page")

    # Users Management
    try:
        page.goto(f"{BASE_URL}/admin/users", wait_until="networkidle")
        page.wait_for_timeout(2000)
        save(page, "11_users_management")
    except:
        print("  [SKIP] users page")

    print("\n=== WEEK 4: Full System - Student Portal ===")
    logout(page)
    page.wait_for_timeout(1500)
    save(page, "12_logout_login_page")

    login(page, STUDENT_EMAIL, STUDENT_PASS)
    page.wait_for_timeout(2000)

    # Student Dashboard
    try:
        page.wait_for_timeout(1500)
        save(page, "13_student_dashboard")
    except:
        print("  [SKIP] student dashboard")

    # Student Profile
    try:
        page.goto(f"{BASE_URL}/student/profile", wait_until="networkidle")
        page.wait_for_timeout(2000)
        save(page, "14_student_profile")
    except:
        print("  [SKIP] student profile")

    # Student Courses
    try:
        page.goto(f"{BASE_URL}/student/courses", wait_until="networkidle")
        page.wait_for_timeout(2000)
        save(page, "15_student_courses")
    except:
        print("  [SKIP] student courses")

    # Student Attendance
    try:
        page.goto(f"{BASE_URL}/student/attendance", wait_until="networkidle")
        page.wait_for_timeout(2000)
        save(page, "16_student_attendance")
    except:
        print("  [SKIP] student attendance")

    # Student Marks
    try:
        page.goto(f"{BASE_URL}/student/marks", wait_until="networkidle")
        page.wait_for_timeout(2000)
        save(page, "17_student_marks")
    except:
        print("  [SKIP] student marks")

    # Faculty view
    logout(page)
    page.wait_for_timeout(1000)
    login(page, FACULTY_EMAIL, FACULTY_PASS)
    page.wait_for_timeout(2000)
    save(page, "18_faculty_dashboard")

    browser.close()

print("\n✅ All screenshots saved to:", SCREENSHOT_DIR)
print("Files:", sorted(os.listdir(SCREENSHOT_DIR)))
