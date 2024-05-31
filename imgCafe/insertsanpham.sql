use N11_QuanLyCaPhe

ALTER TABLE SanPham
ALTER COLUMN loaiSP nvarchar(50);

ALTER TABLE SanPham
ALTER COLUMN tenSP nvarchar(50);


INSERT into SanPham (maSP, tenSP, loaiSP, hinhAnhSP, giaSP)
VALUES ('1', N'Cà phê sữa', N'Cafe', (SELECT BulkColumn FROM OPENROWSET(BULK 'D:\imgCafe\cf1.png', SINGLE_BLOB) AS HinhAnh), 25000),
('2', N'Cà phê đen', 'Cafe', (SELECT BulkColumn FROM OPENROWSET(BULK 'D:\imgCafe\cf2.png', SINGLE_BLOB) AS HinhAnh), 20000),
('3', N'Bạc sỉu', 'Cafe', (SELECT BulkColumn FROM OPENROWSET(BULK 'D:\imgCafe\cf3.png', SINGLE_BLOB) AS HinhAnh), 30000),
('4', N'Cappuccino', 'Cafe', (SELECT BulkColumn FROM OPENROWSET(BULK 'D:\imgCafe\cf4.png', SINGLE_BLOB) AS HinhAnh), 40000),
('5',N'Mocha', N'Cafe', (SELECT BulkColumn FROM OPENROWSET(BULK 'D:\imgCafe\cf5.png', SINGLE_BLOB) AS HinhAnh), 37000)
INSERT into SanPham (maSP, tenSP, loaiSP, hinhAnhSP, giaSP) VALUES
('15', N'Caramel', N'Cafe', (SELECT BulkColumn FROM OPENROWSET(BULK 'D:\imgCafe\cf6.png', SINGLE_BLOB) AS HinhAnh), 50000),
('16',N'Cà phê robusta', N'Cafe', (SELECT BulkColumn FROM OPENROWSET(BULK 'D:\imgCafe\cf7.png', SINGLE_BLOB) AS HinhAnh), 37000)

INSERT into SanPham (maSP, tenSP, loaiSP, hinhAnhSP, giaSP)
VALUES ('6', N'Trà đào', N'Trà', (SELECT BulkColumn FROM OPENROWSET(BULK 'D:\imgCafe\tra1.png', SINGLE_BLOB) AS HinhAnh), 27000),
('7', N'Trà ổi', N'Trà', (SELECT BulkColumn FROM OPENROWSET(BULK 'D:\imgCafe\tra2.png', SINGLE_BLOB) AS HinhAnh), 30000),
('8', N'Trà vải', N'Trà', (SELECT BulkColumn FROM OPENROWSET(BULK 'D:\imgCafe\tra3.png', SINGLE_BLOB) AS HinhAnh), 32000),
('9', N'Trà chanh', N'Trà', (SELECT BulkColumn FROM OPENROWSET(BULK 'D:\imgCafe\tra4.png', SINGLE_BLOB) AS HinhAnh), 20000),
('10',N'Trà dâu', N'Trà', (SELECT BulkColumn FROM OPENROWSET(BULK 'D:\imgCafe\tra5.png', SINGLE_BLOB) AS HinhAnh), 30000)


INSERT into SanPham (maSP, tenSP, loaiSP, hinhAnhSP, giaSP)
VALUES ('11', N'Gà rán', N'Ăn vặt', (SELECT BulkColumn FROM OPENROWSET(BULK 'D:\imgCafe\av1.png', SINGLE_BLOB) AS HinhAnh), 30000),
('12', N'Khoai tây chiên', N'Ăn vặt', (SELECT BulkColumn FROM OPENROWSET(BULK 'D:\imgCafe\av2.png', SINGLE_BLOB) AS HinhAnh), 25000),
('13', N'Cá viên chiên', N'Ăn vặt', (SELECT BulkColumn FROM OPENROWSET(BULK 'D:\imgCafe\av3.png', SINGLE_BLOB) AS HinhAnh), 2500),
('14', N'Bánh mì que',N'Ăn vặt', (SELECT BulkColumn FROM OPENROWSET(BULK 'D:\imgCafe\av4.png', SINGLE_BLOB) AS HinhAnh), 20000)



ALTER TABLE SanPham
ALTER COLUMN loaiSP nvarchar(50);
