SQLiteDatabase appdb = openOrCreateDatabase("spartascout.db",MODE_PRIVATE,null);
Cursor resultSet = appdb.rawQuery("Select * from Routes",null);
resultSet.moveToFirst();
String via = resultSet.getString(2);
Integer duration = resultSet.getInt(5);

