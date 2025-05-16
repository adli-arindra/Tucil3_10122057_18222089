package utils;

public interface Listener {
    void onSearch(String algorithm, String heuristic);
    void onFileSelected(String filePath);
}
