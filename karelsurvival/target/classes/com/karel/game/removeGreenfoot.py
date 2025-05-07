import os

def remove_lines_with_greenfoot(directory):
    for root, _, files in os.walk(directory):
        for file in files:
            if file.endswith(".java"):
                file_path = os.path.join(root, file)
                with open(file_path, 'r', encoding='utf-8') as f:
                    lines = f.readlines()

                # Filter out lines containing "greenfoot" (case-sensitive)
                new_lines = [line for line in lines if "greenfoot" not in line]

                # Only write if changes were made
                if len(new_lines) != len(lines):
                    with open(file_path, 'w', encoding='utf-8') as f:
                        f.writelines(new_lines)
                    print(f"Removed lines containing 'greenfoot' from: {file_path}")

if __name__ == "__main__":
    directory = input("Enter the directory path to process: ").strip()
    if os.path.isdir(directory):
        remove_lines_with_greenfoot(directory)
        print("Done!")
    else:
        print("Error: The specified directory does not exist.")