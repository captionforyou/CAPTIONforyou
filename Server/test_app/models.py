from django.db import models

# Create your models here.


class Notice(models.Model):
    no = models.AutoField(primary_key=True)
    nickname = models.TextField(blank=True, null=True)  # This field type is a guess.
    status = models.IntegerField(blank=True, null=True)
    subboardnum = models.IntegerField(db_column='subBoardNum', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'notice'


class Study(models.Model):
    no = models.AutoField(primary_key=True)
    link = models.TextField(blank=True, null=True)  # This field type is a guess.
    language = models.TextField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'study'


class Subboard(models.Model):
    no = models.AutoField(primary_key=True)
    link = models.TextField(blank=True, null=True)  # This field type is a guess.
    status = models.IntegerField(blank=True, null=True)
    registernickname = models.TextField(blank=True, null=True) 
    registerno = models.IntegerField(blank=True, null=True)
    requestnickname = models.TextField(blank=True, null=True) 
    language = models.TextField(blank=True, null=True)
    contents = models.TextField(blank=True, null=True)
    tax = models.IntegerField(blank=True, null=True)
    israted = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'subBoard'


class Userinfo(models.Model):
    no = models.AutoField(primary_key=True)
    id = models.TextField(blank=True, null=True)  # This field type is a guess.
    password = models.TextField(blank=True, null=True)  # This field type is a guess.
    nickname = models.TextField(blank=True, null=True)  # This field type is a guess.
    points = models.IntegerField(blank=True, null=True)
    money = models.IntegerField(blank=True, null=True)
    requestnum = models.IntegerField(db_column='requestNum', blank=True, null=True)  # Field name made lowercase.
    registernum = models.IntegerField(db_column='registerNum', blank=True, null=True)  # Field name made lowercase.
    ratingcompleteness = models.FloatField(db_column='ratingCompleteness', blank=True, null=True)  # Field name made lowercase.
    ratingclarity = models.FloatField(db_column='ratingClarity', blank=True, null=True)  # Field name made lowercase.
    ratingtime = models.FloatField(db_column='ratingTime', blank=True, null=True)  # Field name made lowercase.
    ratingcnt = models.IntegerField(db_column='ratingCnt', blank=True, null=True)  # Field name made lowercase.
    noticecnt = models.IntegerField(db_column='noticeCnt', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'userInfo'