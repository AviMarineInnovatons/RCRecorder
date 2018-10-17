package in.avimarine.rcrecorder.dao;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import in.avimarine.rcrecorder.objects.Boat;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 12/10/2018.
 */
public class BoatRepository {

  private BoatDao mBoatDao;
  private LiveData<List<Boat>> mAllBoats;

  BoatRepository(Application application) {
    BoatRoomDatabase db = BoatRoomDatabase.getDatabase(application);
    mBoatDao = db.boatDao();
    mAllBoats = mBoatDao.getAll();
  }

  LiveData<List<Boat>> getAllBoats() {
    return mAllBoats;
  }


  public void insert (Boat boat) {
    new insertAsyncTask(mBoatDao).execute(boat);
  }

  public void delete(Boat boat) {
    new removeAsyncTask(mBoatDao).execute(boat);
  }

  public void update(Boat boat) {
    new updateAsyncTask(mBoatDao).execute(boat);
  }

  private static class insertAsyncTask extends AsyncTask<Boat, Void, Void> {

    private BoatDao mAsyncTaskDao;

    insertAsyncTask(BoatDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final Boat... params) {
      mAsyncTaskDao.insertAll(params[0]);
      return null;
    }
  }
  private static class removeAsyncTask extends AsyncTask<Boat, Void, Void> {

    private BoatDao mAsyncTaskDao;

    removeAsyncTask(BoatDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final Boat... params) {
      mAsyncTaskDao.delete(params[0]);
      return null;
    }
  }

  private class updateAsyncTask extends AsyncTask<Boat, Void, Void>{

    private BoatDao mAsyncTaskDao;
    public updateAsyncTask(BoatDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(Boat... boats) {
      mAsyncTaskDao.update(boats[0]);
      return null;
    }
  }
}