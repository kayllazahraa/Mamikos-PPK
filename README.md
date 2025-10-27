# 🏠 Layanan Informasi Kos Mahasiswa STIS — RESTful API (Java Spring Boot)

## 📘 Deskripsi Proyek
Proyek ini merupakan **Pemenuhan Ujian Tengah Semester (UTS)** untuk mata kuliah **Pemrograman Platform Khusus**, dengan topik:
> **“Layanan Informasi Kos Mahasiswa STIS Berbasis RESTful Web Service dengan Java Spring Boot.”**

Aplikasi ini menyediakan sistem backend yang memfasilitasi interaksi antara **pencari kos** dan **pemilik kos**.  
Dibangun menggunakan **Java Spring Boot** dengan arsitektur **layered architecture** (Controller–Service–Repository–Entity) dan dilengkapi **keamanan JWT (JSON Web Token)** untuk autentikasi stateless.

---

## 👩‍💻 Disusun Oleh
| Nama | NIM | Kelas |
|------|-----|-------|
| **Kaylla Zahrani** | 222313161 | 3SI1/18 |

Politeknik Statistika STIS — 2025

---

## ⚙️ Teknologi yang Digunakan
| Komponen | Teknologi |
|-----------|------------|
| **Framework Backend** | Spring Boot 3.5.7 |
| **Security** | Spring Security, JWT (JJWT 0.11.5), BCrypt |
| **Database** | MySQL, Spring Data JPA (Hibernate ORM) |
| **Build Tool** | Maven |
| **Language** | Java 17 (LTS) |
| **Validation** | Jakarta Validation (javax.validation) |
| **Helper** | Lombok |

---

## 🧩 Arsitektur Sistem
Proyek ini menerapkan **layered architecture**:
- **Entity Layer** — Representasi tabel database (Users, Kosan, Orders, Ulasan)
- **Repository Layer** — Akses data ke database menggunakan JPA
- **Service Layer** — Business logic utama
- **Controller Layer** — REST API endpoint
- **DTO Layer** — Pemisah antara entity dan representasi data eksternal
- **Security Layer** — Autentikasi & otorisasi berbasis JWT
- **Exception Handler** — Penanganan error global dengan kode HTTP yang sesuai

---

## 🧱 Struktur Database
Tabel utama:
- **Users** — Menyimpan data pengguna (ROLE_PENCARI / ROLE_PEMILIK)
- **Kosan** — Informasi properti kos yang ditawarkan pemilik
- **Orders** — Data transaksi pemesanan kos
- **Ulasan** — Rating dan komentar untuk kos

Constraint penting:
- Username unik  
- Rating 1–5  
- Status order: `PENDING`, `APPROVED`, `REJECTED`, `CANCELLED`

---

## 🔐 Sistem Autentikasi (JWT)
- Token dibuat saat login atau registrasi dan berlaku 24 jam.
- Disertakan di setiap request header:  
  `Authorization: Bearer <token>`
- Role-based authorization:
  - `ROLE_PENCARI` — dapat membuat order & ulasan
  - `ROLE_PEMILIK` — dapat mengelola data kos & order

---

## 📡 Endpoint Utama (Ringkasan)
| Kategori | Method | Endpoint | Akses |
|-----------|---------|-----------|--------|
| **Auth** | POST | `/api/auth/register` | Publik |
|           | POST | `/api/auth/login` | Publik |
|           | GET/PUT | `/api/auth/me` | Authenticated |
| **Kosan** | GET | `/api/kosan`, `/api/kosan/{id}` | Publik |
|           | POST/PUT/DELETE | `/api/kosan/**` | ROLE_PEMILIK |
| **Order** | POST | `/api/orders` | ROLE_PENCARI |
|           | GET | `/api/orders/my-bookings` | ROLE_PENCARI |
|           | PUT | `/api/orders/{id}/status` | ROLE_PEMILIK |
| **Ulasan** | GET | `/api/kosan/{kosanId}/ulasan` | Publik |
|            | POST/DELETE | `/api/kosan/{kosanId}/ulasan` | ROLE_PENCARI |

---

## 🧪 Fitur dan Pengujian
✅ **Autentikasi & Manajemen User**
- Registrasi & login berbasis JWT  
- Role-based access control (RBAC)  
- Update profil user  
- Password dienkripsi menggunakan BCrypt  

✅ **Manajemen Kos**
- CRUD listing kos (khusus ROLE_PEMILIK)  
- Public browsing kos tanpa login  
- Status ketersediaan otomatis berubah saat order disetujui  

✅ **Manajemen Order**
- Pencari kos dapat membuat pesanan  
- Pemilik kos dapat melihat & menyetujui/menolak pesanan  
- Validasi status & hak akses antar-role  

✅ **Manajemen Ulasan**
- Pencari hanya dapat memberi ulasan pada kos yang pernah disetujui pesanannya  
- Ulasan dapat dihapus oleh pemiliknya sendiri  
- Semua pengunjung dapat melihat ulasan  

✅ **Keamanan & Validasi**
- JWT-based stateless authentication  
- Global exception handler  
- Validasi input (DTO)  
- Role dan akses endpoint dibatasi  

---
