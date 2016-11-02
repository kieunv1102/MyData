package vn.ce.sale.util;

public class DataMetaData {
	private String _id;
	private String _title;
	private String _datagps;
	private String _time;

	public DataMetaData(String id, String title, String time, String datagps) {
		_id = id;
		_title = title;
		_datagps = datagps;
		_time = time;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_title() {
		return _title;
	}

	public void set_title(String _title) {
		this._title = _title;
	}

	public String get_datagps() {
		return _datagps;
	}

	public void set_datagps(String _datagps) {
		this._datagps = _datagps;
	}

	public String get_time() {
		return _time;
	}

	public void set_time(String _time) {
		this._time = _time;
	}
}
